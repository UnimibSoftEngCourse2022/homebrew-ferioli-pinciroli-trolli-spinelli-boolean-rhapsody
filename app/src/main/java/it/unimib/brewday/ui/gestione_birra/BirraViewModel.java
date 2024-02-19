package it.unimib.brewday.ui.gestione_birra;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.brewday.domain.IGestioneBirra;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;

public class BirraViewModel extends ViewModel {


    //Livedata per pagina creazione birra
    private final MutableLiveData<Risultato> dosaggiRisultato;
    private final MutableLiveData<Risultato> createBirraRisultato;
    private final MutableLiveData<Risultato> consumoIngredientiRisultato;
    private final MutableLiveData<Risultato> attrezziSelezionatiRisultato;

    private final IGestioneBirra gestioneBirraDomain;

    public BirraViewModel(IGestioneBirra gestioneBirraDomain) {

        this.gestioneBirraDomain = gestioneBirraDomain;

        createBirraRisultato = new MutableLiveData<>();
        consumoIngredientiRisultato = new MutableLiveData<>();
        dosaggiRisultato = new MutableLiveData<>();
        attrezziSelezionatiRisultato = new MutableLiveData<>();
    }

    public void createBirra(Birra birra, List<Integer> listaDifferenzaIngredienti, List<Attrezzo> listaAttrezzi) {
        gestioneBirraDomain.createBirra(birra, listaDifferenzaIngredienti, listaAttrezzi, createBirraRisultato::postValue);
    }

    public void calcolaDosaggi(long idRicetta, int litriBirraScelti){
        gestioneBirraDomain.getDosaggiIngredienti(idRicetta, litriBirraScelti, dosaggiRisultato::postValue);
    }

    public void calcolaConsumoIngredienti(List<IngredienteRicetta> ingredientiRicetta) {
        gestioneBirraDomain.getConsumoIngredienti(ingredientiRicetta, consumoIngredientiRisultato::postValue);
    }


    public void getAndOptimizeAttrezziLiberi(int litriScelti) {
        gestioneBirraDomain.getAndOptimizeAttrezziLiberi(litriScelti, attrezziSelezionatiRisultato::postValue);
    }

    /*
     * Metodi per ottenere riferimento a Mutable live data
     */
    public LiveData<Risultato> getCreateBirraResult() {
        return createBirraRisultato;
    }

    public LiveData<Risultato> getAttrezziSelezionatiRisultato() {
        return attrezziSelezionatiRisultato;
    }

    public LiveData<Risultato> getConsumoIngredientiRisultato(){ return consumoIngredientiRisultato; }

    public LiveData<Risultato> getDosaggiRisultato(){return dosaggiRisultato;}
}
