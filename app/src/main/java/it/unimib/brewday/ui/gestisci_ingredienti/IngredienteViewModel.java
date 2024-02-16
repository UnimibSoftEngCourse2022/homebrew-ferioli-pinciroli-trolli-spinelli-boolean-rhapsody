package it.unimib.brewday.ui.gestisci_ingredienti;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brewday.repository.IngredientiRepository;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.Ingrediente;

public class IngredienteViewModel extends ViewModel {
    private final MutableLiveData<Risultato>  readAllIngredientiMutableLiveData;

    private final MutableLiveData<Risultato>  updateIngredienteMutableLiveData ;

    IngredientiRepository ingredientiRepository;

    public IngredienteViewModel(IngredientiRepository ingredientiRepository) {
        readAllIngredientiMutableLiveData = new MutableLiveData<>();
        updateIngredienteMutableLiveData = new MutableLiveData<>();
        this.ingredientiRepository = ingredientiRepository;
    }

    public void readAllIngredienti(){
        ingredientiRepository.readAllIngredienti(readAllIngredientiMutableLiveData::postValue);
    }

    public void updateIngrediente(Ingrediente ingrediente){
        ingredientiRepository.updateIngrediente(ingrediente, updateIngredienteMutableLiveData::postValue);
    }

    public LiveData<Risultato> getReadAllIngredientiResult () {
        return readAllIngredientiMutableLiveData;
    }

    public LiveData<Risultato> getUpdateIngredienteResult () {
        return updateIngredienteMutableLiveData;
    }
}
