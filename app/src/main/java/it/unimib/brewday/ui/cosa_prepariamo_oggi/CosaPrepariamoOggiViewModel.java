package it.unimib.brewday.ui.cosa_prepariamo_oggi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.domain.GestioneBirre;
import it.unimib.brewday.domain.IGestioneBirra;
import it.unimib.brewday.domain.StrategiaOttimizzazione;
import it.unimib.brewday.model.Risultato;

public class CosaPrepariamoOggiViewModel extends ViewModel {

    private MutableLiveData<Risultato> cosaPrepariamoOggiRisultato;
    private final IGestioneBirra gestioneBirra;


    public CosaPrepariamoOggiViewModel(GestioneBirre gestioneBirre) {
        this.gestioneBirra = gestioneBirre;
        cosaPrepariamoOggiRisultato = new MutableLiveData<>();
    }

    public void cosaPrepariamoOggi(StrategiaOttimizzazione strategiaOttimizzazione){
        gestioneBirra.cosaPrepariamoOggi(cosaPrepariamoOggiRisultato::postValue, strategiaOttimizzazione);
    }

    public LiveData<Risultato> getCosaPrepariamoOggiRisultato() {
        return cosaPrepariamoOggiRisultato;
    }

    public void pulisciDati(){
        cosaPrepariamoOggiRisultato = new MutableLiveData<>();
    }

}
