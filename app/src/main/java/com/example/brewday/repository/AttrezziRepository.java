package com.example.brewday.repository;

import com.example.brewday.database.AttrezzoDao;
import com.example.brewday.database.LocalDatabase;
import com.example.brewday.model.Attrezzo;
import com.example.brewday.model.Risultato;
import com.example.brewday.ui.Callback;

import java.util.List;

public class AttrezziRepository {

    private final AttrezzoDao attrezziDao;

    public AttrezziRepository(LocalDatabase localDatabase) {
        attrezziDao = localDatabase.attrezzoDao();
    }

    public void readAllAttrezzi(Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<Attrezzo> allAttrezzi = attrezziDao.getAll();

            if(allAttrezzi != null) {
                callback.onComplete(new Risultato.AttrezziSuccess(allAttrezzi));
            }
            else {
                callback.onComplete(new Risultato.Errore(new Exception("Errore nella lettura degli attrezzi")));
            }
        });
    }

    public void createAttrezzi(Callback callback, Attrezzo attrezzo){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            long id = attrezziDao.insertAttrezzo(attrezzo);

            if(id != -1) {
                callback.onComplete(new Risultato.Errore(new Exception("Errore nell'inserimento degli attrezzi")));
            }
            else{
                callback.onComplete(new Risultato.Success());
            }
        });
    }

    public void updateAttrezzi(Callback callback, Attrezzo attrezzo) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int rowsUpdated = attrezziDao.updateAttrezzo(attrezzo);
            if (rowsUpdated == 0) {
                callback.onComplete(new Risultato.Errore(new Exception("Errore nell'aggiornamento dei dati")));
            }
            else{
                callback.onComplete(new Risultato.Success());
            }
        });
    }

    public void deleteAttrezzi(Callback callback, Attrezzo attrezzo) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int rowsDeleted = attrezziDao.deleteAttrezzo(attrezzo);
            if (rowsDeleted == 0) {
                callback.onComplete(new Risultato.Errore(new Exception("Errore nella cancellazione dei dati")));
            }
            else{
                callback.onComplete(new Risultato.Success());
            }
        });
    }

}
