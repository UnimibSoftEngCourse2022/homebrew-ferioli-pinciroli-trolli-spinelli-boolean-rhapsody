package it.unimib.brewday.domain;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.AttrezzoBirra;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.repository.AttrezziRepository;
import it.unimib.brewday.repository.BirreRepository;
import it.unimib.brewday.repository.IngredientiRepository;
import it.unimib.brewday.repository.RicetteRepository;
import it.unimib.brewday.ui.Callback;
import it.unimib.brewday.util.Ottimizzazione;

public class GestioneBirreDomain implements IGestioneBirraDomain{

    //Repository di accesso ai dati
    private final BirreRepository birreRepository;
    private final IngredientiRepository ingredientiRepository;
    private final RicetteRepository ricetteRepository;
    private final AttrezziRepository attrezziRepository;

    public GestioneBirreDomain(BirreRepository birreRepository,
                               IngredientiRepository ingredientiRepository,
                               RicetteRepository ricetteRepository,
                               AttrezziRepository attrezziRepository) {

        this.birreRepository = birreRepository;
        this.ingredientiRepository = ingredientiRepository;
        this.ricetteRepository = ricetteRepository;
        this.attrezziRepository = attrezziRepository;
    }

    @Override
    public void getAllBirre(Callback callback) {
        birreRepository.readAllBirre(callback);
    }

    @Override
    public void createBirra(Birra birra, List<Integer> listaDifferenzaIngredienti, List<Attrezzo> listaAttrezzi, Callback callback) {
        List<AttrezzoBirra> attrezziBirra = new ArrayList<>();
        for (Attrezzo attrezzo : listaAttrezzi) {
            attrezziBirra.add(new AttrezzoBirra(birra.getId(), attrezzo.getId()));
        }

        birreRepository.createBirra(birra, attrezziBirra, risultatoBirra -> {
            if (risultatoBirra.isSuccessful()){

                ingredientiRepository.readAllIngredienti(risultatoIngredienti -> {

                    if (risultatoIngredienti.isSuccessful()){
                        List<Ingrediente> listaIngredientiDisponibili = ((Risultato.ListaIngredientiSuccesso) risultatoIngredienti).getData();
                        for (int i = 0; i < listaIngredientiDisponibili.size(); i++) {
                            listaIngredientiDisponibili.get(i).setQuantitaPosseduta(listaDifferenzaIngredienti.get(i));
                        }
                        ingredientiRepository.updateAllIngredienti(listaIngredientiDisponibili, callback);
                    }
                });
            }
            else {
                callback.onComplete(new Risultato.Errore("Errore nella creazione della birra"));
            }
        });
    }

    @Override
    public void updateBirra(Birra birra, Callback callback){
        birreRepository.updateBirra(birra, callback);
    }

    @Override
    public void terminaBirra(Birra birra, Callback callback) {
        birreRepository.terminaBirra(birra, callback);
    }

    @Override
    public void getIngredientiBirra (Birra birra, Callback callback){
        ricetteRepository.readIngredientiRicetta(birra.getIdRicetta(), risultato -> {
            if (risultato.isSuccessful()) {
                List<IngredienteRicetta> listaIngredientiBirra = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();

                setDosaggioDaIngredienteRicetta(birra.getLitriProdotti(), listaIngredientiBirra );
                callback.onComplete(new Risultato.ListaIngredientiDellaRicettaSuccesso(listaIngredientiBirra));
            }
        });
    }

    @Override
    public void getAttrezziBirra(Birra birra, Callback callback) {
        attrezziRepository.readAttrezziBirra(birra.getId(), callback);
    }

    @Override
    public void getDosaggiIngredienti(long idRicetta, int litriBirraScelti, Callback callback) {
        ricetteRepository.readIngredientiRicetta(idRicetta, risultatoIngredientiRicetta -> {

            if(risultatoIngredientiRicetta.isSuccessful()){

                List<IngredienteRicetta> ingredientiRicetta =
                        ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultatoIngredientiRicetta).getListaIngrediente();

                setDosaggioDaIngredienteRicetta(litriBirraScelti , ingredientiRicetta);
                callback.onComplete(new Risultato.ListaIngredientiDellaRicettaSuccesso(ingredientiRicetta));
            }
        });
    }

    @Override
    public void getConsumoIngredienti(List<IngredienteRicetta> ingredientiRicetta, Callback callback) {
        ingredientiRepository.readAllIngredienti(risultatoIngredienti -> {

            if(risultatoIngredienti.isSuccessful()){

                List<Ingrediente> listaIngredientiDisponibili =
                        ((Risultato.ListaIngredientiSuccesso) risultatoIngredienti).getData();

                List<Integer> listaDifferenzaIngredienti = new ArrayList<>();
                calcolaDifferenzaIngredienti(listaIngredientiDisponibili, ingredientiRicetta ,listaDifferenzaIngredienti);
                callback.onComplete(new Risultato.ListaDifferenzaIngredientiSuccesso(listaDifferenzaIngredienti));
            }
        });
    }

    @Override
    public void getAndOptimizeAttrezziLiberi(int litriScelti, Callback callback) {
        attrezziRepository.readAllAttrezziNonInUso(risultato -> {
            if (risultato.isSuccessful()){
                List<Attrezzo> listaAttrezziDisponibili = ((Risultato.ListaAttrezziSuccesso) risultato).getAttrezzi();

                Risultato risultatoOttimizzazione = Ottimizzazione.ottimizzaAttrezzi(listaAttrezziDisponibili, litriScelti);
                callback.onComplete(risultatoOttimizzazione);

            }
            else{
                callback.onComplete(risultato);
            }
        });
    }


    /*
     * Metodi di supporto per la gestione del calclo della differenza tra gli ingredienti
     */
    private void setDosaggioDaIngredienteRicetta(int litriBirraScelti,
                                                 List<IngredienteRicetta> listaIngredientiRicetta ){
        for (IngredienteRicetta ingredienteRicetta : listaIngredientiRicetta) {
            if (ingredienteRicetta.getTipoIngrediente().equals(TipoIngrediente.ACQUA)) {
                ingredienteRicetta.setDosaggioIngrediente(round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti));
            } else {
                ingredienteRicetta.setDosaggioIngrediente(Math.round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti));
            }
        }
    }

    private void calcolaDifferenzaIngredienti(List<Ingrediente> listaIngredientiDisponibili,
                                              List<IngredienteRicetta> listaIngredientiBirra,
                                              List<Integer> listaDifferenzaIngredienti){
        for(int i=0; i < listaIngredientiBirra.size(); i++){
            int differenza =  listaIngredientiDisponibili.get(i).getQuantitaPosseduta()
                    - ((int) Math.round(listaIngredientiBirra.get(i).getDosaggioIngrediente()));
            listaDifferenzaIngredienti.add(differenza);
        }
    }

    private static double round(double n) {
        return Math.floor(n * Math.pow(10, 1)) / Math.pow(10, 1);
    }
}
