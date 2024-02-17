package it.unimib.brewday.ui.cosa_preparare_oggi;

import androidx.lifecycle.ViewModel;

import it.unimib.brewday.domain.GestioneBirreDomain;

public class CosaPreparareOggiViewModel extends ViewModel {

    private final GestioneBirreDomain gestioneBirreDomain;

    public CosaPreparareOggiViewModel(GestioneBirreDomain gestioneBirreDomain) {
        this.gestioneBirreDomain = gestioneBirreDomain;
    }
}
