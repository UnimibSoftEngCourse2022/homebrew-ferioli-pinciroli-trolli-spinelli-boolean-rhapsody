package it.unimib.brewday.ui.gestisci_attrezzi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.IAttrezziRepository;

public class AttrezziViewModel extends ViewModel {

    private final MutableLiveData<Risultato> allAttrezziRisultato;
    private final MutableLiveData<Risultato> createAttrezzoRisultato;
    private final MutableLiveData<Risultato> updateAttrezzoRisultato;
    private final MutableLiveData<Risultato> deleteAttrezzoRisultato;

    private final IAttrezziRepository attrezziRepository;

    public AttrezziViewModel(IAttrezziRepository attrezziRepository) {

        allAttrezziRisultato = new MutableLiveData<>();
        createAttrezzoRisultato = new MutableLiveData<>();
        updateAttrezzoRisultato = new MutableLiveData<>();
        deleteAttrezzoRisultato = new MutableLiveData<>();

        this.attrezziRepository = attrezziRepository;
    }

    public void readAllAttrezzi() {
        attrezziRepository.readAllAttrezzi(allAttrezziRisultato::postValue);
    }

    public void createAttrezzo(Attrezzo attrezzo) {
        attrezziRepository.createAttrezzo(attrezzo, createAttrezzoRisultato::postValue);
    }

    public void updateAttrezzo(Attrezzo nuovoAttrezzo) {
        attrezziRepository.updateAttrezzo(nuovoAttrezzo, updateAttrezzoRisultato::postValue);
    }

    public void deleteAttrezzo(Attrezzo daCancellare) {
        attrezziRepository.deleteAttrezzo(daCancellare, deleteAttrezzoRisultato::postValue);
    }

    public LiveData<Risultato> getAllAttrezziResult() {
        return allAttrezziRisultato;
    }

    public LiveData<Risultato> getCreateAttrezzoRisultato() {
        return createAttrezzoRisultato;
    }

    public LiveData<Risultato> getDeleteAttrezzoRisultato() {
        return deleteAttrezzoRisultato;
    }

    public LiveData<Risultato> getUpdateAttrezzoRisultato() {
        return updateAttrezzoRisultato;
    }
}