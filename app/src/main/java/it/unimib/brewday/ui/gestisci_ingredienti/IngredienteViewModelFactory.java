package it.unimib.brewday.ui.gestisci_ingredienti;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.brewday.util.ServiceLocator;

public class IngredienteViewModelFactory implements ViewModelProvider.Factory{

 private final Context context;

    public IngredienteViewModelFactory(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        return (T) new IngredienteViewModel(ServiceLocator.getInstance().getIngredienteRepository(context));
    }
}
