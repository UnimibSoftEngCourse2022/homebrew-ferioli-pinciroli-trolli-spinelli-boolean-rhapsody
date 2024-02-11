package it.unimib.brewday.ui.gestione_birra;

import androidx.lifecycle.ViewModel;

import it.unimib.brewday.repository.BirraRepository;

public class BirraViewModel extends ViewModel {

    private final BirraRepository birraRepository;

    public BirraViewModel(BirraRepository birraRepository) {
        this.birraRepository = birraRepository;
    }
}
