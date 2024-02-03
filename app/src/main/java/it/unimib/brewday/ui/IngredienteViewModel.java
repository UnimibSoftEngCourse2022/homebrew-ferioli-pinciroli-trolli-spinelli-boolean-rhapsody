package it.unimib.brewday.ui;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.brewday.repository.IngredienteRepository;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.util.ServiceLocator;

public class IngredienteViewModel extends ViewModel {
    MutableLiveData<Risultato>  readAllIngredientiMutableLiveData;

    MutableLiveData<Risultato>  updateIngredientiMutableLiveData ;

    MutableLiveData<Risultato>  updateIngredienteMutableLiveData ;

    IngredienteRepository ingredienteRepository;

    public IngredienteViewModel(Context context) {
        readAllIngredientiMutableLiveData = new MutableLiveData<>();
        updateIngredientiMutableLiveData = new MutableLiveData<>();
        updateIngredienteMutableLiveData = new MutableLiveData<>();
        ingredienteRepository = ServiceLocator.getInstance().getIngredienteRepository(context);
    }




    public void readAllIngredienti(){
    ingredienteRepository.readAllIngredienti(risultato ->
            readAllIngredientiMutableLiveData.postValue(risultato));
    }


    public void updateIngrediente(Ingrediente ingrediente){
        ingredienteRepository.updateIngrediente(ingrediente, risultato ->
                updateIngredienteMutableLiveData.postValue(risultato));
    }

    public void updateIngredienti(List<Ingrediente> listaIngredienti){
        ingredienteRepository.updateAllIngredienti(listaIngredienti, risultato ->
                updateIngredienteMutableLiveData.postValue(risultato));

    }

}
