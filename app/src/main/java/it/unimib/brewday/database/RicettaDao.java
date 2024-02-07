package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.RicettaConIngredienti;

@Dao
public interface RicettaDao {
    @Transaction
    @Query("SELECT * FROM ricette")
    List<Ricetta> getAllRicette();

    @Transaction
    @Query("SELECT * FROM ricette WHERE id = :id")
    List<RicettaConIngredienti> getRicettaById(long id);

    @Insert
    long insertRicetta(Ricetta ricetta);

    @Update
    int updateRicetta(Ricetta ricetta);

    @Delete
    int deleteRicetta(Ricetta ricetta);
}
