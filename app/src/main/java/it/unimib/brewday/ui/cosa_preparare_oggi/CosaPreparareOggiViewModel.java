package it.unimib.brewday.ui.cosa_preparare_oggi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.brewday.domain.GestioneBirre;
import it.unimib.brewday.domain.IGestioneBirraDomain;
import it.unimib.brewday.domain.StrategiaOttimizzazione;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;

public class CosaPreparareOggiViewModel extends ViewModel {

    private final MutableLiveData<Risultato> ricettaConsumoMassimoRisultato;
    private final MutableLiveData<Risultato> dosaggiRisultato;
    private final MutableLiveData<Risultato> consumoIngredientiRisultato;
    private final IGestioneBirraDomain gestioneBirraDomain;


    public CosaPreparareOggiViewModel(GestioneBirre gestioneBirre) {
        this.gestioneBirraDomain = gestioneBirre;
        ricettaConsumoMassimoRisultato = new MutableLiveData<>();
        dosaggiRisultato = new MutableLiveData<>();
        consumoIngredientiRisultato = new MutableLiveData<>();
    }

    public void cosaPrepariamoOggi(StrategiaOttimizzazione strategiaOttimizzazione){
        gestioneBirraDomain.cosaPrepariamoOggi(ricettaConsumoMassimoRisultato::postValue, strategiaOttimizzazione);
    }

    public void calcolaDosaggi(long idRicetta, int litriBirraScelti){
        gestioneBirraDomain.getDosaggiIngredienti(idRicetta, litriBirraScelti, dosaggiRisultato::postValue);
    }

    public void calcolaConsumoIngredienti(List<IngredienteRicetta> ingredientiRicetta) {
        gestioneBirraDomain.getConsumoIngredienti(ingredientiRicetta, consumoIngredientiRisultato::postValue);
    }

    public LiveData<Risultato> getRicettaConsumoMassimoRisultato() {
        return ricettaConsumoMassimoRisultato;
    }

    public LiveData<Risultato> getDosaggiRisultato() {
        return dosaggiRisultato;
    }

    public LiveData<Risultato> getConsumoIngredientiRisultato() {
        return consumoIngredientiRisultato;
    }
}
