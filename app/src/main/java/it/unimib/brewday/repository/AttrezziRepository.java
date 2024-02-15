package it.unimib.brewday.repository;

import it.unimib.brewday.database.AttrezzoDao;
import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.Callback;
import it.unimib.brewday.util.RegistroErrori;

import java.util.List;

public class AttrezziRepository {

    private final AttrezzoDao attrezziDao;

    public AttrezziRepository(LocalDatabase localDatabase) {
        attrezziDao = localDatabase.attrezzoDao();
    }

    public void readAllAttrezzi(Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<Attrezzo> allAttrezzi = attrezziDao.getAllAttrezzi();

            if(allAttrezzi != null) {
                callback.onComplete(new Risultato.ListaAttrezziSuccesso(allAttrezzi));
            }
            else {
                callback.onComplete(new Risultato.Errore(RegistroErrori.ATTREZZI_FETCH_ERROR));
            }
        });
    }

    public void createAttrezzo(Attrezzo attrezzo, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            long id = attrezziDao.insertAttrezzo(attrezzo);

            if(id == -1) {
                callback.onComplete(new Risultato.Errore(RegistroErrori.ATTREZZI_CREATION_ERROR));
            }
            else{
                callback.onComplete(new Risultato.Successo());
            }
        });
    }

    public void updateAttrezzo(Attrezzo attrezzo, Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int rowsUpdated = attrezziDao.updateAttrezzo(attrezzo);
            if (rowsUpdated == 0) {
                callback.onComplete(new Risultato.Errore(RegistroErrori.ATTREZZI_UPDATE_ERROR));
            }
            else{
                callback.onComplete(new Risultato.Successo());
            }
        });
    }

    public void deleteAttrezzo(Attrezzo attrezzo, Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int rowsDeleted = attrezziDao.deleteAttrezzo(attrezzo);
            if (rowsDeleted == 0) {
                callback.onComplete(new Risultato.Errore(RegistroErrori.ATTREZZI_DELETE_ERROR));
            }
            else{
                callback.onComplete(new Risultato.Successo());
            }
        });
    }

    public void readAllAttrezziNonInUso(Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            List<Attrezzo> attrezziNonInUso = attrezziDao.getAllAttrezziNonInUtilizzo();
            if(attrezziNonInUso != null) {
                callback.onComplete(new Risultato.ListaAttrezziSuccesso(attrezziNonInUso));
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.ATTREZZI_FETCH_ERROR));
            }
        });
    }
}
