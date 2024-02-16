package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import it.unimib.brewday.model.Attrezzo;

import java.util.List;

@Dao
public interface AttrezzoDao {

    @Query("SELECT * FROM attrezzo")
    List<Attrezzo> getAllAttrezzi();

    @Query("SELECT * FROM attrezzo " +
            "EXCEPT " +
            "SELECT A.* FROM attrezzobirra AS AB JOIN attrezzo AS A ON AB.idAttrezzo = A.id")
    List<Attrezzo> getAllAttrezziNonInUtilizzo();

    @Insert
    long insertAttrezzo(Attrezzo attrezzo);

    @Update
    int updateAttrezzo(Attrezzo attrezzo);

    @Delete
    int deleteAttrezzo(Attrezzo attrezzo);
}
