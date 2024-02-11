package it.unimib.brewday.ui.gestisci_ricette;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.brewday.util.ServiceLocator;

public class RicetteViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public RicetteViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RicetteViewModel(ServiceLocator.getInstance().getRicetteRepository(context));
    }
}
