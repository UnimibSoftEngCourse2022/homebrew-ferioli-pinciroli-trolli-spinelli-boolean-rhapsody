package it.unimib.brewday.ui.gestisci_ricette;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.RicetteRepository;

public class RicetteViewModel extends ViewModel {

    private final MutableLiveData<Risultato> ricetteRisultato;
    private final MutableLiveData<Risultato> ingredientiRicetteRisultato;
    private final MutableLiveData<Risultato> insertRicettaRisultato;
    private final MutableLiveData<Risultato> updateRicettaRisultato;
    private final MutableLiveData<Risultato> deleteRicettaRisultato;
    private final MutableLiveData<Risultato> updateIngredientiRicettaRisultato;

    private final RicetteRepository ricetteRepository;

    public RicetteViewModel(RicetteRepository ricetteRepository){
        this.ricetteRepository = ricetteRepository;

        ricetteRisultato = new MutableLiveData<>();
        insertRicettaRisultato = new MutableLiveData<>();
        deleteRicettaRisultato = new MutableLiveData<>();
        updateRicettaRisultato = new MutableLiveData<>();
        updateIngredientiRicettaRisultato = new MutableLiveData<>();
        ingredientiRicetteRisultato = new MutableLiveData<>();
    }

    public void getAllRicette() {
        ricetteRepository.readAllRicette(ricetteRisultato::postValue);
    }

    public void insertRicetta(Ricetta ricetta, List<IngredienteRicetta> listaIngredienti) {
        ricetteRepository.insertRicetta(ricetta, listaIngredienti, insertRicettaRisultato::postValue);
    }

    public void deleteRicetta(Ricetta ricetta) {
        ricetteRepository.deleteRicetta(ricetta, deleteRicettaRisultato::postValue);
    }

    public void updateRicetta(Ricetta ricetta) {
        ricetteRepository.updateRicetta(ricetta, updateRicettaRisultato::postValue);
    }

    public void updateIngredientiRicetta(List<IngredienteRicetta> ingredientiRicetta) {
        ricetteRepository.updateIngredientiRicetta(ingredientiRicetta, updateIngredientiRicettaRisultato::postValue);
    }

    public void getIngredientiRicetta(long idRicetta) {
        ricetteRepository.readIngredientiRicetta(idRicetta, ingredientiRicetteRisultato::postValue);
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

    public LiveData<Risultato> getUpdateRicettaRisultato() {
        return updateRicettaRisultato;
    }

    public LiveData<Risultato> getUpdateIngredientiRicettaRisultato() {return updateIngredientiRicettaRisultato;}

    public LiveData<Risultato> getDeleteRicettaRisultato() {
        return deleteRicettaRisultato;
    }
}
