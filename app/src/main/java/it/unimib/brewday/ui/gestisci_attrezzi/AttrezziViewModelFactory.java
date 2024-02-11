package it.unimib.brewday.ui.gestisci_attrezzi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.brewday.util.ServiceLocator;

public class AttrezziViewModelFactory implements ViewModelProvider.Factory {

    public final Context context;

    public AttrezziViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AttrezziViewModel(ServiceLocator.getInstance().getAttrezziRepository(context));
    }
}
