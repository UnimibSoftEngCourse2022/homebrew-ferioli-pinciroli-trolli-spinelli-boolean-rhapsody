package it.unimib.brewday.domain;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.AttrezzoBirra;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.AttrezziRepository;
import it.unimib.brewday.repository.BirreRepository;
import it.unimib.brewday.repository.IngredientiRepository;
import it.unimib.brewday.repository.RicetteRepository;
import it.unimib.brewday.ui.Callback;
import it.unimib.brewday.util.Ottimizzazione;

public class GestioneBirre implements IGestioneBirra {

    //Repository di accesso ai dati
    private final BirreRepository birreRepository;
    private final IngredientiRepository ingredientiRepository;
    private final RicetteRepository ricetteRepository;
    private final AttrezziRepository attrezziRepository;

    public GestioneBirre(BirreRepository birreRepository,
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
                callback.onComplete(risultatoBirra);
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

                GestioneBirreUtil.calcolaDosaggiPerLitriScelti(birra.getLitriProdotti(), listaIngredientiBirra );
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

                GestioneBirreUtil.calcolaDosaggiPerLitriScelti(litriBirraScelti , ingredientiRicetta);
                callback.onComplete(new Risultato.ListaIngredientiDellaRicettaSuccesso(ingredientiRicetta));
            }
            else{
                callback.onComplete(risultatoIngredientiRicetta);
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
                GestioneBirreUtil.calcolaDifferenzaIngredienti(listaIngredientiDisponibili, ingredientiRicetta ,listaDifferenzaIngredienti);
                callback.onComplete(new Risultato.ListaDifferenzaIngredientiSuccesso(listaDifferenzaIngredienti));
            }
            else{
                callback.onComplete(risultatoIngredienti);
            }
        });
    }

    @Override
    public void getAndOptimizeAttrezziLiberi(int litriScelti, Callback callback) {
        attrezziRepository.readAllAttrezziLiberi(risultato -> {
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

    @Override
    public void cosaPrepariamoOggi(Callback callback, StrategiaOttimizzazione strategiaOttimizzazione) {
        attrezziRepository.readAllAttrezziLiberi(attrezziLiberiRisultato -> {
            if(attrezziLiberiRisultato.isSuccessful()){

                List<Attrezzo> listaAttrezziLiberi = ((Risultato.ListaAttrezziSuccesso) attrezziLiberiRisultato).getAttrezzi();

                ricetteRepository.readAllRicette(listaRicetteRisultato -> {
                    if(listaRicetteRisultato.isSuccessful()){

                        List<Ricetta> listaRicette = ((Risultato.ListaRicetteSuccesso) listaRicetteRisultato).getRicette();

                        ricetteRepository.readAllIngredientiRicetta(listaIngredientiRicettaRisultato -> {
                            if(listaIngredientiRicettaRisultato.isSuccessful()){

                                List<IngredienteRicetta> listaIngredientiRicette = ((Risultato.ListaIngredientiDellaRicettaSuccesso) listaIngredientiRicettaRisultato)
                                        .getListaIngrediente();

                                ingredientiRepository.readAllIngredienti(listaIngredientiDisponibiliRisultato -> {
                                    if(listaIngredientiDisponibiliRisultato.isSuccessful()){

                                        List<Ingrediente> listaIngredientiDisponibili = ((Risultato.ListaIngredientiSuccesso) listaIngredientiDisponibiliRisultato)
                                                .getData();

                                        callback.onComplete(strategiaOttimizzazione.ottimizza(
                                                listaAttrezziLiberi,
                                                listaRicette,
                                                listaIngredientiRicette,
                                                listaIngredientiDisponibili
                                        ));
                                    }
                                    else{
                                        callback.onComplete(listaIngredientiDisponibiliRisultato);
                                    }
                                });
                            }
                            else{
                                callback.onComplete(listaIngredientiRicettaRisultato);
                            }
                        });
                    }
                    else{
                        callback.onComplete(listaRicetteRisultato);
                    }
                });
            }
            else{
                callback.onComplete(attrezziLiberiRisultato);
            }
        });
    }
}
