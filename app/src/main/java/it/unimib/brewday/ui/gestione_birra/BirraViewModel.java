package it.unimib.brewday.ui.gestione_birra;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.BirraRepository;

public class BirraViewModel extends ViewModel {

    private final MutableLiveData<Risultato> getAllBirreRisultato;
    private final MutableLiveData<Risultato> createBirraRisultato;
    private final BirraRepository birraRepository;

    public BirraViewModel(BirraRepository birraRepository) {
        this.birraRepository = birraRepository;
        getAllBirreRisultato = new MutableLiveData<>();
        createBirraRisultato = new MutableLiveData<>();
    }

    public void getAllBirre() {
        birraRepository.getAllBirre(getAllBirreRisultato::postValue);
    }

    public void createBirra(Birra birra) {
        birraRepository.createBirra(birra, createBirraRisultato::postValue);
    }

    public LiveData<Risultato> getAllBirreResult() {
        return getAllBirreRisultato;
    }

    public LiveData<Risultato> getCreateBirraResult() {
        return createBirraRisultato;
    }
}
