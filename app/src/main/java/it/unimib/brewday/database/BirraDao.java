package it.unimib.brewday.database;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.BirraConRicetta;

public interface BirraDao {

    @Insert
    long insertBirra(Birra birra);

    @Query("SELECT * FROM Birra ORDER BY terminata ASC")
    List<BirraConRicetta> getAllBirre();
}
