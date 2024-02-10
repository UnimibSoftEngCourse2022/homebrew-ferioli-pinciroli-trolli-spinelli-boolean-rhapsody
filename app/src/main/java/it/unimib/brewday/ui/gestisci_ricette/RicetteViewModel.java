package it.unimib.brewday.ui.gestisci_ricette;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.RicetteRepository;

public class RicetteViewModel extends ViewModel {

    private MutableLiveData<Risultato> ricetteRisultato;
    private MutableLiveData<Risultato> ingredientiRicetteRisultato;
    private MutableLiveData<Risultato> insertRicettaRisultato;
    private MutableLiveData<Risultato> insertIngredientiRicettaRisultato;
    private MutableLiveData<Risultato> updateRicettaRisultato;
    private MutableLiveData<Risultato> updateIngredientiRicettaRisultato;
    private MutableLiveData<Risultato> deleteRicettaRisultato;
    RicetteRepository ricetteRepository;

    public RicetteViewModel(RicetteRepository ricetteRepository){
        this.ricetteRepository = ricetteRepository;
    }

    public void getAllRicette() {

        ricetteRepository.getRicette(risultato ->
            ricetteRisultato.postValue(risultato));
    }

    public LiveData<Risultato> getRicetteRisultato() {
        return ricetteRisultato;
    }

    public LiveData<Risultato> getIngredientiRicetteRisultato() {
        return ingredientiRicetteRisultato;
    }

    public LiveData<Risultato> getInsertRicettaRisultato() {
        return insertRicettaRisultato;
    }

    public LiveData<Risultato> getInsertIngredientiRicettaRisultato() {
        return insertIngredientiRicettaRisultato;
    }

    public LiveData<Risultato> getUpdateRicettaRisultato() {
        return updateRicettaRisultato;
    }

    public LiveData<Risultato> getUpdateIngredientiRicettaRisultato() {
        return updateIngredientiRicettaRisultato;
    }

    public LiveData<Risultato> getDeleteRicettaRisultato() {
        return deleteRicettaRisultato;
    }
}
