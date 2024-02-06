package it.unimib.brewday.ui.gestisci_attrezzi;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.AttrezziRepository;
import it.unimib.brewday.util.ServiceLocator;

public class GestisciAttrezziViewModel extends ViewModel {

    public final MutableLiveData<Risultato> allAttrezzi;
    public final MutableLiveData<Risultato> createAttrezzoResult;
    public final MutableLiveData<Risultato> updateAttrezzoResult;
    public final MutableLiveData<Risultato> deleteAttrezzoResult;
    public final MutableLiveData<Boolean> isAddCardVisible;

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
        attrezziRepository.readAllAttrezzi(allAttrezzi::postValue);
    }

    public void createAttrezzo(Attrezzo attrezzo) {

        attrezziRepository.createAttrezzo(attrezzo, result -> {
            createAttrezzoResult.postValue(result);
            readAllAttrezzi();
        });
    }

    public void updateAttrezzo(Attrezzo nuovoAttrezzo) {
        attrezziRepository.updateAttrezzo(nuovoAttrezzo, updateAttrezzoResult::postValue);
    }

    public void deleteAttrezzo(Attrezzo daCancellare) {
        attrezziRepository.deleteAttrezzo(daCancellare, deleteAttrezzoResult::postValue);
    }
}