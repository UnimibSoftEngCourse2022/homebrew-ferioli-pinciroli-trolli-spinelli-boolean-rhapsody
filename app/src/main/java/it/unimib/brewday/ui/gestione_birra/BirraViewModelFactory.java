package it.unimib.brewday.ui.gestione_birra;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.brewday.util.ServiceLocator;

public class BirraViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public BirraViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BirraViewModel(
                ServiceLocator.getInstance().getBirraRepository(context),
                ServiceLocator.getInstance().getIngredienteRepository(context),
                ServiceLocator.getInstance().getRicetteRepository(context),
                ServiceLocator.getInstance().getAttrezziRepository(context)
        );
    }
}
