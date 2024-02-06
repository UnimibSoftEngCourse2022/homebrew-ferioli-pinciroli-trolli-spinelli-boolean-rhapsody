package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import it.unimib.brewday.model.Ricetta;

@Dao
public interface RicettaDao {
    @Query("SELECT * FROM ricetta")
    List<Ricetta> getAllRicette();

    @Query("SELECT * FROM ricetta WHERE id = :id")
    List<Ricetta> getRicettaById(long id);

    @Query("SELECT * FROM ricetta WHERE nome = :nome")
    List<Ricetta> getRicettaByName(String nome);

    @Insert
    long insertRicetta(Ricetta ricetta);

    @Update
    int updateRicetta(Ricetta ricetta);

    @Delete
    int deleteRicetta(Ricetta ricetta);
}
