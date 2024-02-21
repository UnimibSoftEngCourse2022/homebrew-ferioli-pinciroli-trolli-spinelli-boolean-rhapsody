package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.brewday.model.Ingrediente;

@Dao
public interface IngredienteDao {

    @Query("SELECT * FROM ingrediente ")
    List<Ingrediente> getAllIngredienti();

    @Update int updateAllIngredienti(List<Ingrediente> ingredienti);

    @Update int updateIngrediente(Ingrediente ingrediente);

    @Insert
    List<Long> insertIngredientiList(List<Ingrediente> listaIngrediente);

}
