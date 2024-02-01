package it.unimib.brewday;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.brewday.model.Ingrediente;

public class IngredienteViewModel extends ViewModel {
    MutableLiveData<Risultato>  readAllIngredientiMutableLiveData;

    MutableLiveData<Risultato>  updateIngredientiMutableLiveData ;

    MutableLiveData<Risultato>  updateIngredienteMutableLiveData ;

    IngredienteRepository ingredienteRepository;

    public IngredienteViewModel(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;

        readAllIngredientiMutableLiveData = new MutableLiveData<>();
        updateIngredientiMutableLiveData = new MutableLiveData<>();
        updateIngredienteMutableLiveData = new MutableLiveData<>();
    }




    public void readAllIngredienti(){
    ingredienteRepository.readAllIngredienti(risultato ->
            readAllIngredientiMutableLiveData.postValue(risultato));
    }

    public void UpdateIngrediente(Ingrediente ingrediente){
        ingredienteRepository.updateIngrediente(ingrediente, risultato ->
                updateIngredienteMutableLiveData.postValue(risultato));
    }

    public void UpdateIngredienti(List<Ingrediente> listaIngredienti){
        ingredienteRepository.updateAllIngredienti(listaIngredienti, risultato ->
                updateIngredienteMutableLiveData.postValue(risultato));

    }

}
