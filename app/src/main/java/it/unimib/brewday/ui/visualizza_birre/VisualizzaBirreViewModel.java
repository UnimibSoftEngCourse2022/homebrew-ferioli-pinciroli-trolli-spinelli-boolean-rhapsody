package it.unimib.brewday.ui.visualizza_birre;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.NotaDegustazione;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.repository.AttrezziRepository;
import it.unimib.brewday.repository.BirreRepository;
import it.unimib.brewday.repository.NoteDegustazioneRepository;
import it.unimib.brewday.repository.RicetteRepository;

public class VisualizzaBirreViewModel extends ViewModel {

    //Livedata per pagina birra
    private final MutableLiveData<Risultato> getAllBirreRisultato;
    private final MutableLiveData<Risultato> terminaBirraRisultato;
    private final MutableLiveData<Risultato> getIngredientiBirraRisultato;

    private final MutableLiveData<Risultato> getAttrezziBirraRisultato;

    private final MutableLiveData<Risultato> getNoteDegustazioneRisultato;

    private final MutableLiveData<Risultato> getInserimentoNotaDegustazioneRisultato;
    private final MutableLiveData<Risultato> getMediaNotaDegustazioneRisultato;

    //Repository di accesso ai dati
    private final BirreRepository birreRepository;
    private final RicetteRepository ricetteRepository;

    private final AttrezziRepository attrezziRepository;

    private final NoteDegustazioneRepository noteDegustazioneRepository;

    public VisualizzaBirreViewModel(BirreRepository birreRepository,
                          RicetteRepository ricetteRepository, AttrezziRepository attrezziRepository, NoteDegustazioneRepository noteDegustazioneRepository) {

        this.birreRepository = birreRepository;
        this.ricetteRepository = ricetteRepository;
        this.attrezziRepository = attrezziRepository;
        this.noteDegustazioneRepository = noteDegustazioneRepository;

        getAllBirreRisultato = new MutableLiveData<>();
        terminaBirraRisultato = new MutableLiveData<>();
        getIngredientiBirraRisultato = new MutableLiveData<>();
        getAttrezziBirraRisultato = new MutableLiveData<>();
        getNoteDegustazioneRisultato = new MutableLiveData<>();
        getInserimentoNotaDegustazioneRisultato = new MutableLiveData<>();
        getMediaNotaDegustazioneRisultato = new MutableLiveData<>();
    }

    public void getAllBirre() {
        birreRepository.readAllBirre(getAllBirreRisultato::postValue);
    }

    public void terminaBirra(Birra birra){
        birreRepository.terminaBirra(birra, terminaBirraRisultato::postValue);
    }

    public void getIngredientiBirra (Birra birra){
        ricetteRepository.readIngredientiRicetta(birra.getIdRicetta(), risultato -> {
            if (risultato.isSuccessful()) {
                List<IngredienteRicetta> listaIngredientiBirra = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();
                setDosaggioDaIngredienteRicetta(birra.getLitriProdotti(), listaIngredientiBirra );
                getIngredientiBirraRisultato.postValue(new Risultato.ListaIngredientiDellaRicettaSuccesso(listaIngredientiBirra));
            }
        });
    }

    public void getAttrezziBirra (Birra birra){
        attrezziRepository.readAttrezziBirra(birra.getId(), getAttrezziBirraRisultato::postValue);
    }

    public void getNoteDegustazione(long idBirra){
        noteDegustazioneRepository.readAllNoteDegustazione(idBirra, getNoteDegustazioneRisultato::postValue);
    }

    public void creaNotaDegustazione(NotaDegustazione notaDegustazione){
        noteDegustazioneRepository.inserisciNotaDegustazione(notaDegustazione, getInserimentoNotaDegustazioneRisultato::postValue);
    }

    public void calcolaMediaNotaDegustazione(long idBirra){
        noteDegustazioneRepository.readMediaNotaDegustazione(idBirra, getMediaNotaDegustazioneRisultato::postValue);
    }
    /*
     * Metodi per ottenere riferimento a Mutable live data
     */

    public LiveData<Risultato> getAllBirreResult() {
        return getAllBirreRisultato;
    }

    public LiveData<Risultato> getTerminaBirraRisultato() {
        return terminaBirraRisultato;
    }

    public LiveData<Risultato> getIngredientiBirraRisultato(){return getIngredientiBirraRisultato;}

    public LiveData<Risultato> getAttrezziBirraRisultato(){return getAttrezziBirraRisultato;}

    public LiveData<Risultato> getNoteDegustazioneRisultato(){ return getNoteDegustazioneRisultato;}

    public LiveData<Risultato> getInserimentoNotaDegustazioneRisultato(){ return getInserimentoNotaDegustazioneRisultato;}

    public LiveData<Risultato> getMediaNotaDegustazioneRisultato(){ return getMediaNotaDegustazioneRisultato;}


    private void setDosaggioDaIngredienteRicetta(int litriBirraScelti,
                                                 List<IngredienteRicetta> listaIngredientiRicetta ){
        for (IngredienteRicetta ingredienteRicetta : listaIngredientiRicetta) {
            if (ingredienteRicetta.getTipoIngrediente().equals(TipoIngrediente.ACQUA)) {
                ingredienteRicetta.setDosaggioIngrediente(round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti, 1));
            } else {
                ingredienteRicetta.setDosaggioIngrediente(Math.round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti));
            }
        }
    }

    private static double round(double n, int decimals) {
        return Math.floor(n * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }
}
