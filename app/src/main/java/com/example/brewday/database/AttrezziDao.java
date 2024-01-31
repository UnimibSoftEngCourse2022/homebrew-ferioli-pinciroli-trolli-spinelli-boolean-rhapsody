package com.example.brewday.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.brewday.model.Attrezzo;

import java.util.List;

@Dao
public interface AttrezziDao {

    @Query("SELECT * FROM attrezzo")
    List<Attrezzo> getAll();

    @Insert
    public void insertAttrezzo(Attrezzo attrezzo);

    @Update
    public void updateAttrezzo(Attrezzo attrezzo);

    @Delete
    public void deleteAttrezzo(Attrezzo attrezzo);
}
