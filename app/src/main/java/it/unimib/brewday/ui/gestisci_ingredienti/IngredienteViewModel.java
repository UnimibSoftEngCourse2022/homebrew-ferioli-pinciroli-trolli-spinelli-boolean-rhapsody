package it.unimib.brewday.ui.gestisci_ingredienti;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.brewday.repository.IngredienteRepository;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.util.ServiceLocator;

public class IngredienteViewModel extends ViewModel {
    private final MutableLiveData<Risultato>  readAllIngredientiMutableLiveData;

    private final MutableLiveData<Risultato>  updateIngredienteMutableLiveData ;

    private final MutableLiveData<Risultato>  updateIngredientiMutableLiveData ;

    IngredienteRepository ingredienteRepository;

    public IngredienteViewModel(Context context) {
        readAllIngredientiMutableLiveData = new MutableLiveData<>();
        updateIngredientiMutableLiveData = new MutableLiveData<>();
        updateIngredienteMutableLiveData = new MutableLiveData<>();
        ingredienteRepository = ServiceLocator.getInstance().getIngredienteRepository(context);
    }

    public void readAllIngredienti(){
        ingredienteRepository.readAllIngredienti(readAllIngredientiMutableLiveData::postValue);
    }

    public void updateIngrediente(Ingrediente ingrediente){
        ingredienteRepository.updateIngrediente(ingrediente, updateIngredienteMutableLiveData::postValue);
    }

    public void updateIngredienti(List<Ingrediente> listaIngredienti){
        ingredienteRepository.updateAllIngredienti(listaIngredienti, updateIngredienteMutableLiveData::postValue);
    }

    public LiveData<Risultato> getReadAllIngredientiResult () {
        return readAllIngredientiMutableLiveData;
    }

    public LiveData<Risultato> getUpdateIngredienteResult () {
        return updateIngredienteMutableLiveData;
    }

    public LiveData<Risultato> getUdateIngredientiResult () {
        return updateIngredientiMutableLiveData;
    }
}
