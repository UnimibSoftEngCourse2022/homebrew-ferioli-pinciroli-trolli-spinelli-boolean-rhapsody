package it.unimib.brewday.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class IngredienteViewModelFactory implements ViewModelProvider.Factory{

 private final Context context;

    public IngredienteViewModelFactory(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        return (T) new IngredienteViewModel(context);
    }
}
