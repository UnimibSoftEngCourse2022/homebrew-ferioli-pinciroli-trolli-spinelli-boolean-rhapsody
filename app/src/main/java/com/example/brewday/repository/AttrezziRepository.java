package com.example.brewday.repository;

import com.example.brewday.database.AttrezzoDao;
import com.example.brewday.database.LocalDatabase;
import com.example.brewday.model.Attrezzo;
import com.example.brewday.model.Result;
import com.example.brewday.ui.Callback;

import java.util.List;

public class AttrezziRepository {

    private AttrezzoDao attrezziDao;

    public AttrezziRepository(LocalDatabase localDatabase) {
        attrezziDao = localDatabase.attrezzoDao();
    }

    public void readAllAttrezzi(Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<Attrezzo> allAttrezzi = attrezziDao.getAll();

            if(allAttrezzi != null) {
                callback.onComplete(new Result.AttrezziSuccess(allAttrezzi));
            }
            else {
                callback.onComplete(new Result.Errore(new Exception("Errore nel caricamento degli attrezzi")));
            }
        });
    }

}
