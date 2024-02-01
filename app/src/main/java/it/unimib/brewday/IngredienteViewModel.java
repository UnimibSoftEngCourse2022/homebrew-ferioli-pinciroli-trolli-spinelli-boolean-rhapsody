package it.unimib.brewday;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;

public class IngredienteViewModel extends ViewModel {
    MutableLiveData<Risultato>  readAllIngredientiMutableLiveData;

    MutableLiveData<Risultato>  updateIngredientiMutableLiveData ;

    IngredienteRepository ingredienteRepository;

    public IngredienteViewModel(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;

        readAllIngredientiMutableLiveData = new MutableLiveData<>();
        updateIngredientiMutableLiveData = new MutableLiveData<>();
    }




    public void readAllIngredienti(){
    ingredienteRepository.readAllIngredienti(risultato -> {


            });



    }

}
