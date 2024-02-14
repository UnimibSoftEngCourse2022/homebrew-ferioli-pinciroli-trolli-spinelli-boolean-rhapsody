package it.unimib.brewday.ui.gestisci_attrezzi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.AttrezziRepository;

public class AttrezziViewModel extends ViewModel {

    private final MutableLiveData<Risultato> allAttrezzi;
    private final MutableLiveData<Risultato> createAttrezzoResult;
    private final MutableLiveData<Risultato> updateAttrezzoResult;
    private final MutableLiveData<Risultato> deleteAttrezzoResult;
    private final MutableLiveData<Risultato> allAttrezziNonInUsoResult;

    private final AttrezziRepository attrezziRepository;

    public AttrezziViewModel(AttrezziRepository attrezziRepository) {

        allAttrezzi = new MutableLiveData<>();
        createAttrezzoResult = new MutableLiveData<>();
        updateAttrezzoResult = new MutableLiveData<>();
        deleteAttrezzoResult = new MutableLiveData<>();
        allAttrezziNonInUsoResult = new MutableLiveData<>();

        this.attrezziRepository = attrezziRepository;
    }

    public void readAllAttrezzi() {
        attrezziRepository.readAllAttrezzi(allAttrezzi::postValue);
    }

    public void createAttrezzo(Attrezzo attrezzo) {
        attrezziRepository.createAttrezzo(attrezzo, createAttrezzoResult::postValue);
    }

    public void updateAttrezzo(Attrezzo nuovoAttrezzo) {
        attrezziRepository.updateAttrezzo(nuovoAttrezzo, updateAttrezzoResult::postValue);
    }

    public void deleteAttrezzo(Attrezzo daCancellare) {
        attrezziRepository.deleteAttrezzo(daCancellare, deleteAttrezzoResult::postValue);
    }

    public void readAttrezziNonInUso() {
        attrezziRepository.readAllAttrezziInUso(allAttrezziNonInUsoResult::postValue);
    }

    public LiveData<Risultato> getAllAttrezziResult() {
        return allAttrezzi;
    }

    public LiveData<Risultato> getCreateAttrezzoResult() {
        return createAttrezzoResult;
    }

    public LiveData<Risultato> getDeleteAttrezzoResult() {
        return deleteAttrezzoResult;
    }

    public LiveData<Risultato> getUpdateAttrezzoResult() {
        return updateAttrezzoResult;
    }

    public LiveData<Risultato> getAllAttrezziDisponibiliResult() {
        return allAttrezziNonInUsoResult;
    }
}