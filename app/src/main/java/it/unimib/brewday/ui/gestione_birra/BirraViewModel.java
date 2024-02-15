package it.unimib.brewday.ui.gestione_birra;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.BirreRepository;

public class BirraViewModel extends ViewModel {

    private final MutableLiveData<Risultato> getAllBirreRisultato;
    private final MutableLiveData<Risultato> createBirraRisultato;
    private final MutableLiveData<Risultato> updateBirraRisultato;
    private final BirreRepository birreRepository;


    public BirraViewModel(BirreRepository birreRepository) {
        this.birreRepository = birreRepository;
        getAllBirreRisultato = new MutableLiveData<>();
        createBirraRisultato = new MutableLiveData<>();
        updateBirraRisultato = new MutableLiveData<>();
    }

    public void getAllBirre() {
        birreRepository.readAllBirre(getAllBirreRisultato::postValue);
    }

    public void createBirra(Birra birra) {
        birreRepository.createBirra(birra, createBirraRisultato::postValue);
    }

    public void updateBirra(Birra birra){
        birreRepository.updateBirra(birra, updateBirraRisultato::postValue);
    }

    public LiveData<Risultato> getAllBirreResult() {
        return getAllBirreRisultato;
    }

    public LiveData<Risultato> getCreateBirraResult() {
        return createBirraRisultato;
    }

    public LiveData<Risultato> getUpdateBirraRisultato(){
        return updateBirraRisultato;
    }
}
