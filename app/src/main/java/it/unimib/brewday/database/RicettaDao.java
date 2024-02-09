package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.IngredienteRicetta;

@Dao
public interface RicettaDao {

    @Query("SELECT * FROM ricetta")
    List<Ricetta> getRicette();

    @Insert
    long insertRicetta(Ricetta ricetta);

    @Update
    int updateRicetta(Ricetta ricetta);

    @Query(
            "SELECT idRicetta, tipoIngrediente, dosaggioIngrediente " +
                    "FROM IngredienteRicetta WHERE idRicetta = :idRicetta"
    )
    List<IngredienteRicetta> getIngredientiDellaRicetta(int idRicetta);

    @Insert
    long[] insertRicettaIngrediente(List<IngredienteRicetta> listaIngredientiRicetta);

    @Update
    int updateRicettaIngrediente(List<IngredienteRicetta> listaIngredientiRicetta);

}
