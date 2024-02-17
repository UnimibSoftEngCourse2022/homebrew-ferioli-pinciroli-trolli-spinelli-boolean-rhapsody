package it.unimib.brewday.ui.cosa_preparare_oggi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.brewday.util.ServiceLocator;

public class CosaPreparareOggiViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public CosaPreparareOggiViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CosaPreparareOggiViewModel(ServiceLocator.getInstance().getGestioneBirraDomain(context));
    }
}
