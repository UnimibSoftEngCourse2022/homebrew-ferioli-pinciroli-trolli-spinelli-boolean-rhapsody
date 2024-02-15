package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.IngredienteRicetta;

@Dao
public interface RicettaDao {

    @Query("SELECT * FROM ricetta")
    List<Ricetta> getAllRicette();

    @Insert
    long insertRicetta(Ricetta ricetta);

    @Update
    int updateRicetta(Ricetta ricetta);

    @Delete
    int deleteRicetta(Ricetta ricetta);

    @Query(
            "SELECT idRicetta, tipoIngrediente, dosaggioIngrediente " +
                    "FROM IngredienteRicetta WHERE idRicetta = :idRicetta ORDER BY tipoIngrediente ASC"
    )
    List<IngredienteRicetta> getIngredientiRicetta(long idRicetta);

    @Insert
    long[] insertIngredientiRicetta(List<IngredienteRicetta> listaIngredientiRicetta);

    @Update
    int updateIngredientiRicetta(List<IngredienteRicetta> ingredienteRicetta);
}
