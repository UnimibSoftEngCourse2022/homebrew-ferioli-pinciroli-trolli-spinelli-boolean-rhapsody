package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import it.unimib.brewday.model.NotaDegustazione;

@Dao
public interface NotaDegustazioneDao {

    @Insert
    long inserisciNota(NotaDegustazione notaDegustazione);

    @Query("SELECT * FROM NotaDegustazione where NotaDegustazione.idBirra == :idBirra")
    List<NotaDegustazione> getListaNoteDegustazione(long idBirra);









}
