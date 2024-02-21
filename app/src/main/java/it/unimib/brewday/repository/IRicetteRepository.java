package it.unimib.brewday.repository;

import java.util.List;

import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.ui.Callback;

public interface IRicetteRepository {

    void insertRicetta(Ricetta ricetta, List<IngredienteRicetta> listaDegliIngredienti, Callback callback);
    void readIngredientiRicetta(long idRicetta, Callback callback);
    void readAllIngredientiRicetta(Callback callback);
    void readAllRicette(Callback callback);
    void updateRicetta(Ricetta ricetta, Callback callback);
    void deleteRicetta(Ricetta ricetta, Callback callback);
    void updateIngredientiRicetta(List<IngredienteRicetta> ingredienteRicetta, Callback callback);
}
