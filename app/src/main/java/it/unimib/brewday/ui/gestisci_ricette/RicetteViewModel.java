package it.unimib.brewday.ui.gestisci_ricette;

import androidx.lifecycle.ViewModel;
import it.unimib.brewday.repository.RicetteRepository;

public class RicetteViewModel extends ViewModel {

    private final RicetteRepository ricetteRepository;

    public RicetteViewModel(RicetteRepository ricetteRepository){
        this.ricetteRepository = ricetteRepository;
    }

}
