package it.unimib.brewday.repository;

import java.util.List;

import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.ui.Callback;

public interface IIngredientiRepository {

    void readAllIngredienti(Callback callback);
    void updateAllIngredienti(List<Ingrediente> listaIngredienti, Callback  callback);
    void updateIngrediente(Ingrediente ingrediente, Callback  callback);
}
