package it.unimib.brewday.ui.visualizza_birre;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.brewday.util.ServiceLocator;

public class VisualizzaBirreViewModelFactory implements ViewModelProvider.Factory{
    private final Context context;

    public VisualizzaBirreViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new VisualizzaBirreViewModel(
                ServiceLocator.getInstance().getBirraRepository(context),
                ServiceLocator.getInstance().getRicetteRepository(context),
                ServiceLocator.getInstance().getAttrezziRepository(context)
        );
    }
}
