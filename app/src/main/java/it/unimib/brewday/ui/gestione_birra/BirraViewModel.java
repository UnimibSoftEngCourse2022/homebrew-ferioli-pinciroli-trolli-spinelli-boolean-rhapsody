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

    //Livedata per pagina birra
    private final MutableLiveData<Risultato> getAllBirreRisultato;
    private final MutableLiveData<Risultato> createBirraRisultato;
    private final MutableLiveData<Risultato> terminaBirraRisultato;

    //Livedata per pagina creazione birra
    private final MutableLiveData<Risultato> ingredientiRicettaPerLitriRisultato;
    private final MutableLiveData<Risultato> differenzaIngredientiRisultato;
    private final MutableLiveData<Risultato> attrezziSelezionatiRisultato;

    private final IGestioneBirraDomain domainGestioneBirra;

    public BirraViewModel(IGestioneBirraDomain domainGestioneBirra) {

        this.domainGestioneBirra = domainGestioneBirra;

        getAllBirreRisultato = new MutableLiveData<>();
        createBirraRisultato = new MutableLiveData<>();
        terminaBirraRisultato = new MutableLiveData<>();

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

    public void getIngredientiPerLitriScelti(long idRicetta, int litriBirraScelti){
        domainGestioneBirra.getIngredientiRicettaBirra(idRicetta, litriBirraScelti, ingredientiRicettaPerLitriRisultato::postValue);
    }

    public void getDifferenzaIngredienti(List<IngredienteRicetta> ingredientiRicetta) {
        domainGestioneBirra.getDifferenzaIngredientiRicettaBirra(ingredientiRicetta, differenzaIngredientiRisultato::postValue);
    }


    public void getAndOptimizeAttrezziLiberi(int litriScelti) {
        domainGestioneBirra.getAndOptimizeAttrezziLiberi(litriScelti, attrezziSelezionatiRisultato::postValue);
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

    public LiveData<Risultato> getDifferenzaIngredientiRisultato(){ return  differenzaIngredientiRisultato; }

    public LiveData<Risultato> getIngredientiRicettaPerLitriRisultato(){return ingredientiRicettaPerLitriRisultato;}
}
