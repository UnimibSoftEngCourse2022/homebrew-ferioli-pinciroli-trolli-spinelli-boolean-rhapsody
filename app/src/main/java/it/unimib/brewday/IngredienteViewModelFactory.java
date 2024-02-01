package it.unimib.brewday;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class IngredienteViewModelFactory implements ViewModelProvider.Factory{

 private final IngredienteRepository ingredienteRepository;


    public IngredienteViewModelFactory(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        return (T) new IngredienteViewModel(ingredienteRepository);
    }
}
