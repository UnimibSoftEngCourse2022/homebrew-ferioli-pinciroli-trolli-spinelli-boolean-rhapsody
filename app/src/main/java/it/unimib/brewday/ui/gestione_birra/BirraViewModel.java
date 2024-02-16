package it.unimib.brewday.ui.gestione_birra;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
import it.unimib.brewday.util.Ottimizzazione;

public class BirraViewModel extends ViewModel {

    //Livedata per pagina birra
    private final MutableLiveData<Risultato> getAllBirreRisultato;
    private final MutableLiveData<Risultato> createBirraRisultato;
    private final MutableLiveData<Risultato> terminaBirraRisultato;

    //Livedata per pagina creazione birra
    private final MutableLiveData<Risultato> ingredientiRicettaPerLitriRisultato;
    private final MutableLiveData<Risultato> differenzaIngredientiRisultato;
    private final MutableLiveData<Risultato> attrezziSelezionatiRisultato;
    private final MutableLiveData<Risultato>  updateIngredientiMutableLiveData ;

    //Repository di accesso ai dati
    private final BirreRepository birreRepository;
    private final IngredientiRepository ingredientiRepository;
    private final RicetteRepository ricetteRepository;
    private final AttrezziRepository attrezziRepository;

    public BirraViewModel(BirreRepository birreRepository,
                          IngredientiRepository ingredientiRepository,
                          RicetteRepository ricetteRepository,
                          AttrezziRepository attrezziRepository) {

        this.birreRepository = birreRepository;
        this.ingredientiRepository = ingredientiRepository;
        this.ricetteRepository = ricetteRepository;
        this.attrezziRepository = attrezziRepository;

        getAllBirreRisultato = new MutableLiveData<>();
        createBirraRisultato = new MutableLiveData<>();
        terminaBirraRisultato = new MutableLiveData<>();

        differenzaIngredientiRisultato = new MutableLiveData<>();
        ingredientiRicettaPerLitriRisultato = new MutableLiveData<>();
        attrezziSelezionatiRisultato = new MutableLiveData<>();
        updateIngredientiMutableLiveData = new MutableLiveData<>();
    }

    public void getAllBirre() {
        birreRepository.readAllBirre(getAllBirreRisultato::postValue);
    }

    public void createBirra(Birra birra, List<Integer> listaDifferenzaIngredienti, List<Attrezzo> listaAttrezzi) {

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
                        ingredientiRepository.updateAllIngredienti(listaIngredientiDisponibili, updateIngredientiMutableLiveData::postValue);
                    }
                });
                createBirraRisultato.postValue(new Risultato.Successo());

            } else {
                createBirraRisultato.postValue(new Risultato.Errore("Errore nella creazione della Birra"));
            }
        });
    }

    public void terminaBirra(Birra birra){
        birreRepository.terminaBirra(birra, terminaBirraRisultato::postValue);
    }

    public void getDifferenzaIngredienti(long idRicetta, int litriBirraScelti){

        ricetteRepository.readIngredientiRicetta(idRicetta, risultatoIngredientiRicetta -> {

            if(risultatoIngredientiRicetta.isSuccessful()){

                List<IngredienteRicetta> ingredientiRicetta =
                        ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultatoIngredientiRicetta).getListaIngrediente();

                setDosaggioDaIngredienteRicetta(litriBirraScelti , ingredientiRicetta);
                ingredientiRicettaPerLitriRisultato.postValue(new Risultato.ListaIngredientiDellaRicettaSuccesso(ingredientiRicetta));

                ingredientiRepository.readAllIngredienti(risultatoIngredienti -> {

                    if(risultatoIngredienti.isSuccessful()){

                        List<Ingrediente> listaIngredientiDisponibili =
                                ((Risultato.ListaIngredientiSuccesso) risultatoIngredienti).getData();

                        List<Integer> listaDifferenzaIngredienti = new ArrayList<>();
                        calcolaDifferenzaIngredienti(listaIngredientiDisponibili,ingredientiRicetta ,listaDifferenzaIngredienti);
                        differenzaIngredientiRisultato.postValue(new Risultato.ListaDifferenzaIngredientiSuccesso(listaDifferenzaIngredienti));
                    }
                });
            }

        });

    }


    public void getAndOptimizeAttrezziLiberi(int litriScelti) {
        attrezziRepository.readAllAttrezziNonInUso(risultato -> {
            if (risultato.isSuccessful()){
                List<Attrezzo> listaAttrezziDisponibili = ((Risultato.ListaAttrezziSuccesso) risultato).getAttrezzi();

                Risultato risultatoOttimizzazione = Ottimizzazione.ottimizzaAttrezzi(listaAttrezziDisponibili, litriScelti);
                attrezziSelezionatiRisultato.postValue(risultatoOttimizzazione);

            }
            else{
                attrezziSelezionatiRisultato.postValue(risultato);
            }
        });
    }

    /*
     * Metodi per ottenere riferimento a Mutable live data
     */
    public LiveData<Risultato> getAllBirreResult() {
        return getAllBirreRisultato;
    }

    public LiveData<Risultato> getCreateBirraResult() {
        return createBirraRisultato;
    }

    public LiveData<Risultato> getTerminaBirraRisultato() {
        return terminaBirraRisultato;
    }

    public LiveData<Risultato> getAttrezziSelezionatiRisultato() {
        return attrezziSelezionatiRisultato;
    }

    public LiveData<Risultato> getDifferenzaIngredientiRisultato(){
        return  differenzaIngredientiRisultato;
    }

    public LiveData<Risultato> getIngredientiRicettaPerLitriRisultato(){return ingredientiRicettaPerLitriRisultato;}

    public LiveData<Risultato> getUpdateIngredientiResult() { return updateIngredientiMutableLiveData;}

    /*
     * Metodi di supporto per la gestione del calclo della differenza tra gli ingredienti
     */
    private void setDosaggioDaIngredienteRicetta(int litriBirraScelti,
                                                 List<IngredienteRicetta> listaIngredientiRicetta ){
        for (IngredienteRicetta ingredienteRicetta : listaIngredientiRicetta) {
            if (ingredienteRicetta.getTipoIngrediente().equals(TipoIngrediente.ACQUA)) {
                ingredienteRicetta.setDosaggioIngrediente(round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti, 1));
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

    private static double round(double n, int decimals) {
        return Math.floor(n * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }
}
