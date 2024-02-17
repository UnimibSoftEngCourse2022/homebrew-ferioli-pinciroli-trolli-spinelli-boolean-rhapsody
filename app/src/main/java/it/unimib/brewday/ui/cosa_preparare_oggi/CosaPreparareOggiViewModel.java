package it.unimib.brewday.ui.cosa_preparare_oggi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.domain.GestioneBirreDomain;
import it.unimib.brewday.domain.IGestioneBirraDomain;
import it.unimib.brewday.model.Risultato;

public class CosaPreparareOggiViewModel extends ViewModel {

    private MutableLiveData<Risultato> ricettaConsumoMassimoRisultato;
    private final IGestioneBirraDomain gestioneBirreDomain;


    public CosaPreparareOggiViewModel(GestioneBirreDomain gestioneBirreDomain) {
        this.gestioneBirreDomain = gestioneBirreDomain;
    }

    public void suggerisciRicettaConConsumoMassimo(){
        gestioneBirreDomain.massimizzaConsumoIngredienti(ricettaConsumoMassimoRisultato::postValue);
    }

    public LiveData<Risultato> getRicettaConsumoMassimoRisultato() {
        return ricettaConsumoMassimoRisultato;
    }
}
