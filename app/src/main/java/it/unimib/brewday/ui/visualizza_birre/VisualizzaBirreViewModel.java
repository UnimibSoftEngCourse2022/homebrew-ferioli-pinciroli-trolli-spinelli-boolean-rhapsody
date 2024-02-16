package it.unimib.brewday.ui.visualizza_birre;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.repository.BirreRepository;
import it.unimib.brewday.repository.RicetteRepository;

public class VisualizzaBirreViewModel extends ViewModel {

    //Livedata per pagina birra
    private final MutableLiveData<Risultato> getAllBirreRisultato;
    private final MutableLiveData<Risultato> terminaBirraRisultato;
    private final MutableLiveData<Risultato> getIngredientiBirraRisultato;

    //Repository di accesso ai dati
    private final BirreRepository birreRepository;
    private final RicetteRepository ricetteRepository;

    public VisualizzaBirreViewModel(BirreRepository birreRepository,
                          RicetteRepository ricetteRepository) {

        this.birreRepository = birreRepository;
        this.ricetteRepository = ricetteRepository;

        getAllBirreRisultato = new MutableLiveData<>();
        terminaBirraRisultato = new MutableLiveData<>();
        getIngredientiBirraRisultato = new MutableLiveData<>();

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
}
