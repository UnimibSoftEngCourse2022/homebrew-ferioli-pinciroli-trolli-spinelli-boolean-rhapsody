package it.unimib.brewday.ui.gestisciAttrezzi;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoAttrezzo;
import it.unimib.brewday.repository.AttrezziRepository;
import it.unimib.brewday.util.ServiceLocator;

public class GestisciAttrezziViewModel extends ViewModel {

    public MutableLiveData<Risultato> allAttrezzi;
    public MutableLiveData<Risultato> createAttrezzoResult;
    public MutableLiveData<Risultato> updateAttrezzoResult;
    public MutableLiveData<Risultato> deleteAttrezzoResult;

    public MutableLiveData<Boolean> isAddCardVisible;

    private final AttrezziRepository attrezziRepository;

    public GestisciAttrezziViewModel(Context context) {

        allAttrezzi = new MutableLiveData<>();
        createAttrezzoResult = new MutableLiveData<>();
        updateAttrezzoResult = new MutableLiveData<>();
        deleteAttrezzoResult = new MutableLiveData<>();

        isAddCardVisible = new MutableLiveData<>(false);

        attrezziRepository = ServiceLocator.getInstance().getAttrezziRepository(context);
    }

    public void readAllAttrezzi() {
        attrezziRepository.readAllAttrezzi(result -> {
            allAttrezzi.postValue(result);
        });
    }

    public void createAttrezzo(Attrezzo attrezzo) {

        attrezziRepository.createAttrezzo(attrezzo, result -> {
            createAttrezzoResult.postValue(result);
            readAllAttrezzi();
        });
    }

    public void updateAttrezzo(Attrezzo nuovoAttrezzo) {
        attrezziRepository.updateAttrezzo(nuovoAttrezzo, result -> {
            updateAttrezzoResult.postValue(result);
        });
    }

    public void deleteAttrezzo(Attrezzo daCanellare) {
        attrezziRepository.deleteAttrezzo(daCanellare, result -> {
            deleteAttrezzoResult.postValue(result);
        });
    }
}