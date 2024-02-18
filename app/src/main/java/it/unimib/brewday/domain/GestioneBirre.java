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

public class GestioneBirre implements IGestioneBirraDomain{

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
                callback.onComplete(new Risultato.Errore("Errore nella creazione della birra"));
            }
        });
    }

    @Override
    public void terminaBirra(Birra birra, Callback callback) {
        birreRepository.terminaBirra(birra, callback);
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
    public void massimizzaConsumoIngredienti(Callback callback) {
        attrezziRepository.readAllAttrezziLiberi(attrezziLiberiRisultato -> {
            if(attrezziLiberiRisultato.isSuccessful()){

                int litriMassimiAttrezzi = Ottimizzazione.suggerisciLitri(((Risultato.ListaAttrezziSuccesso) attrezziLiberiRisultato).getAttrezzi());

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

                                        double consumoMassimo = -1.0;
                                        int litriPerRicettaSelezionata = 0;
                                        Ricetta ricettaSelezionata = null;

                                        for (Ricetta ricetta : listaRicette) {
                                            List<IngredienteRicetta> listaIngredientiDiQuestaRicetta =
                                                    GestioneBirreUtil.getIngredientiRicettaByIdRicetta(listaIngredientiRicette, ricetta.getId());

                                            int litriMassimiPerRicetta = Ottimizzazione.litriPerRicetta(listaIngredientiDiQuestaRicetta, listaIngredientiDisponibili);

                                            if(litriMassimiPerRicetta < litriMassimiAttrezzi){
                                                GestioneBirreUtil.calcolaDosaggiPerLitriScelti(litriMassimiPerRicetta, listaIngredientiDiQuestaRicetta);
                                                double consumoTotale = GestioneBirreUtil.calcolaConsumoTotale(listaIngredientiDiQuestaRicetta);

                                                if(consumoTotale > consumoMassimo){
                                                    consumoMassimo = consumoTotale;
                                                    litriPerRicettaSelezionata = litriMassimiPerRicetta;
                                                    ricettaSelezionata = ricetta;
                                                }
                                            }
                                            else{
                                                GestioneBirreUtil.calcolaDosaggiPerLitriScelti(litriMassimiAttrezzi, listaIngredientiDiQuestaRicetta);
                                                double consumoTotale = GestioneBirreUtil.calcolaConsumoTotale(listaIngredientiDiQuestaRicetta);

                                                if(consumoTotale > consumoMassimo){
                                                    consumoMassimo = consumoTotale;
                                                    litriPerRicettaSelezionata = litriMassimiAttrezzi;
                                                    ricettaSelezionata = ricetta;
                                                }
                                            }

                                        }

                                        callback.onComplete(new Risultato.MassimizzazioneConsumoIngredientiSuccesso(ricettaSelezionata, litriPerRicettaSelezionata));

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

    @Override
    public void massimizzaProduzioneLitri(Callback callback) {
        attrezziRepository.readAllAttrezziLiberi(attrezziLiberiRisultato -> {
            if(attrezziLiberiRisultato.isSuccessful()){

                int litriMassimiAttrezzi = Ottimizzazione.suggerisciLitri(((Risultato.ListaAttrezziSuccesso) attrezziLiberiRisultato).getAttrezzi());

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

                                        int litriPerRicettaSelezionata = -1;
                                        Ricetta ricettaSelezionata = null;

                                        for (Ricetta ricetta : listaRicette) {
                                            List<IngredienteRicetta> listaIngredientiDiQuestaRicetta =
                                                    GestioneBirreUtil.getIngredientiRicettaByIdRicetta(listaIngredientiRicette, ricetta.getId());

                                            int litriMassimiRicetta = Ottimizzazione.litriPerRicetta(listaIngredientiDiQuestaRicetta, listaIngredientiDisponibili);

                                            if(litriMassimiRicetta > litriPerRicettaSelezionata){
                                                litriPerRicettaSelezionata = litriMassimiRicetta;
                                                ricettaSelezionata = ricetta;
                                            }
                                        }

                                        if(litriPerRicettaSelezionata > litriMassimiAttrezzi){
                                            litriPerRicettaSelezionata = litriMassimiAttrezzi;
                                        }

                                        callback.onComplete(new Risultato.MassimizzazioneConsumoIngredientiSuccesso(ricettaSelezionata, litriPerRicettaSelezionata));

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
