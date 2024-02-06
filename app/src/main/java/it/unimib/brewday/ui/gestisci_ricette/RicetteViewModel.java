package it.unimib.brewday.ui.gestisci_ricette;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.RicetteRepository;
import it.unimib.brewday.ui.Callback;

public class RicetteViewModel extends ViewModel {

    private final RicetteRepository ricetteRepository;
    private MutableLiveData<Risultato> readListaRicetteRisultato;
    private MutableLiveData<Risultato> readRicettaByIdRisultato;
    private MutableLiveData<Risultato> readRicettaByNameRisultato;
    private MutableLiveData<Risultato> createRicettaRisultato;
    private MutableLiveData<Risultato> updateRicettaRisultato;
    private MutableLiveData<Risultato> deleteRicettaRisultato;

    public RicetteViewModel(RicetteRepository ricetteRepository){
        this.ricetteRepository = ricetteRepository;
    }

    public LiveData<Risultato> readAllRicette(){
        ricetteRepository.readListaRicette(risultato -> {
            readListaRicetteRisultato.postValue(risultato);
        });
        return readListaRicetteRisultato;
    }

}
