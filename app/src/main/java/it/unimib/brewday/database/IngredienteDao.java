package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.brewday.model.Ingrediente;

@Dao
public interface IngredienteDao {

    @Query("SELECT * FROM Ingrediente ")
    List<Ingrediente> getAll();

    @Update int updateAllIngredienti(List<Ingrediente> Ingredienti);

    @Update int updateIngrediente(Ingrediente ingrediente);

    @Insert
    List<Long> insertEventList(List<Ingrediente> listaIngrediente);

}
