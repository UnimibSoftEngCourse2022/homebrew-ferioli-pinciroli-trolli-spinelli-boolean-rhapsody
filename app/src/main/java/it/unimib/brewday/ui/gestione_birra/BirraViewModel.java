package it.unimib.brewday.ui.gestione_birra;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.brewday.domain.IGestioneBirraDomain;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;

public class BirraViewModel extends ViewModel {


    //Livedata per pagina creazione birra
    private final MutableLiveData<Risultato> dosaggiRisultato;
    private final MutableLiveData<Risultato> consumoIngredientiRisultato;
    private final MutableLiveData<Risultato> createBirraRisultato;
    private final MutableLiveData<Risultato> ingredientiRicettaPerLitriRisultato;
    private final MutableLiveData<Risultato> differenzaIngredientiRisultato;
    private final MutableLiveData<Risultato> attrezziSelezionatiRisultato;
    private final MutableLiveData<Risultato>  updateIngredientiMutableLiveData ;

    private final IGestioneBirraDomain domainGestioneBirra;

    public BirraViewModel(IGestioneBirraDomain domainGestioneBirra) {

        this.domainGestioneBirra = domainGestioneBirra;

        createBirraRisultato = new MutableLiveData<>();
        terminaBirraRisultato = new MutableLiveData<>();

        consumoIngredientiRisultato = new MutableLiveData<>();
        dosaggiRisultato = new MutableLiveData<>();
        differenzaIngredientiRisultato = new MutableLiveData<>();
        ingredientiRicettaPerLitriRisultato = new MutableLiveData<>();
        attrezziSelezionatiRisultato = new MutableLiveData<>();
    }

    public void getAllBirre() {
        domainGestioneBirra.getAllBirre(getAllBirreRisultato::postValue);
    }

    public void createBirra(Birra birra, List<Integer> listaDifferenzaIngredienti, List<Attrezzo> listaAttrezzi) {
        domainGestioneBirra.createBirra(birra, listaDifferenzaIngredienti, listaAttrezzi, createBirraRisultato::postValue);
    }

    public void terminaBirra(Birra birra){
        domainGestioneBirra.terminaBirra(birra, terminaBirraRisultato::postValue);
    }

    public void calcolaDosaggi(long idRicetta, int litriBirraScelti){
        domainGestioneBirra.getDosaggiIngredienti(idRicetta, litriBirraScelti, dosaggiRisultato::postValue);
    }

    public void calcolaConsumoIngredienti(List<IngredienteRicetta> ingredientiRicetta) {
        domainGestioneBirra.getConsumoIngredienti(ingredientiRicetta, consumoIngredientiRisultato::postValue);
    }


    public void getAndOptimizeAttrezziLiberi(int litriScelti) {
        domainGestioneBirra.getAndOptimizeAttrezziLiberi(litriScelti, attrezziSelezionatiRisultato::postValue);
    }

    /*
     * Metodi per ottenere riferimento a Mutable live data
     */
    public LiveData<Risultato> getCreateBirraResult() {
        return createBirraRisultato;
    }

    public LiveData<Risultato> getAttrezziSelezionatiRisultato() {
        return attrezziSelezionatiRisultato;
    }

    public LiveData<Risultato> getConsumoIngredientiRisultato(){ return consumoIngredientiRisultato; }

    public LiveData<Risultato> getDosaggiRisultato(){return dosaggiRisultato;}
}
