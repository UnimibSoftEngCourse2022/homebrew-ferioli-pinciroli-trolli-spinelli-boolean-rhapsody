package it.unimib.brewday.ui.visualizza_birre;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import it.unimib.brewday.domain.IGestioneBirra;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.NotaDegustazione;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.INoteDegustazioneRepository;

public class VisualizzaBirreViewModel extends ViewModel {

    //Livedata per pagina birra
    private final MutableLiveData<Risultato> getAllBirreRisultato;
    private final MutableLiveData<Risultato> updateBirreRisultato;
    private final MutableLiveData<Risultato> terminaBirraRisultato;
    private final MutableLiveData<Risultato> getIngredientiBirraRisultato;
    private final MutableLiveData<Risultato> getAttrezziBirraRisultato;
    private final MutableLiveData<Risultato> getNoteDegustazioneRisultato;
    private final MutableLiveData<Risultato> getInserimentoNotaDegustazioneRisultato;

    //Repository di accesso ai dati
    private final INoteDegustazioneRepository noteDegustazioneRepository;

    private final IGestioneBirra gestioneBirra;

    public VisualizzaBirreViewModel(INoteDegustazioneRepository noteDegustazioneRepository, IGestioneBirra gestioneBirra) {

        this.noteDegustazioneRepository = noteDegustazioneRepository;
        this.gestioneBirra = gestioneBirra;

        getAllBirreRisultato = new MutableLiveData<>();
        updateBirreRisultato = new MutableLiveData<>();
        terminaBirraRisultato = new MutableLiveData<>();
        getIngredientiBirraRisultato = new MutableLiveData<>();
        getAttrezziBirraRisultato = new MutableLiveData<>();
        getNoteDegustazioneRisultato = new MutableLiveData<>();
        getInserimentoNotaDegustazioneRisultato = new MutableLiveData<>();

    }

    public void getAllBirre() {
        gestioneBirra.getAllBirre(getAllBirreRisultato::postValue);
    }

    public void updateBirra(Birra birra){
        gestioneBirra.updateBirra(birra, updateBirreRisultato::postValue);
    }

    public void terminaBirra(Birra birra){
        gestioneBirra.terminaBirra(birra, terminaBirraRisultato::postValue);
    }

    public void getIngredientiBirra (Birra birra){
        gestioneBirra.getIngredientiBirra(birra, getIngredientiBirraRisultato::postValue);
    }

    public void getAttrezziBirra (Birra birra){
        gestioneBirra.getAttrezziBirra(birra, getAttrezziBirraRisultato::postValue);
    }

    public void getNoteDegustazione(long idBirra){
        noteDegustazioneRepository.readAllNoteDegustazione(idBirra, getNoteDegustazioneRisultato::postValue);
    }

    public void creaNotaDegustazione(NotaDegustazione notaDegustazione){
        noteDegustazioneRepository.inserisciNotaDegustazione(notaDegustazione, getInserimentoNotaDegustazioneRisultato::postValue);
    }


    /*
     * Metodi per ottenere riferimento a Mutable live data
     */

    public LiveData<Risultato> getAllBirreRisultato() {
        return getAllBirreRisultato;
    }
    public LiveData<Risultato> getUpdateBirreRisultato() {
        return updateBirreRisultato;
    }
    public LiveData<Risultato> getTerminaBirraRisultato() {
        return terminaBirraRisultato;
    }

    public LiveData<Risultato> getIngredientiBirraRisultato(){return getIngredientiBirraRisultato;}

    public LiveData<Risultato> getAttrezziBirraRisultato(){return getAttrezziBirraRisultato;}

    public LiveData<Risultato> getNoteDegustazioneRisultato(){ return getNoteDegustazioneRisultato;}

    public LiveData<Risultato> getInserimentoNotaDegustazioneRisultato(){ return getInserimentoNotaDegustazioneRisultato;}

}
