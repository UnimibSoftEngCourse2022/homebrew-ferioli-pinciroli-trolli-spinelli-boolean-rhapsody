package it.unimib.brewday.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GestisciAttrezziViewModelFactory implements ViewModelProvider.Factory {

    public final Context context;

    public GestisciAttrezziViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GestisciAttrezziViewModel(context);
    }
}
