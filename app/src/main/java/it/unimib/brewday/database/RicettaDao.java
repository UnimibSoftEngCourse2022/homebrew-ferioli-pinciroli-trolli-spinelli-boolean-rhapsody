package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import it.unimib.brewday.model.Ricetta;

@Dao
public interface RicettaDao {

    @Insert
    long insertRicetta(Ricetta ricetta);

    @Update
    int updateRicetta(Ricetta ricetta);

    @Delete
    int deleteRicetta(Ricetta ricetta);
}
