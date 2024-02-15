package it.unimib.brewday.repository;

import java.util.List;

import it.unimib.brewday.database.BirraDao;
import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.model.AttrezzoBirra;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.BirraConRicetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.Callback;
import it.unimib.brewday.util.RegistroErrori;

public class BirreRepository {

    private final BirraDao birraDao;

    public BirreRepository(LocalDatabase localDatabase) {
        birraDao = localDatabase.birraDao();
    }

    public void readAllBirre(Callback callback) {

        LocalDatabase.databaseWriteExecutor.execute(() -> {

            List<BirraConRicetta> allBirre = birraDao.getAllBirre();

            if(allBirre != null) {
                callback.onComplete(new Risultato.AllBirreSuccesso(allBirre));
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.BIRRE_FETCH_ERROR));
            }
        });
    }

    public void createBirra(Birra birra, Callback callback) {

        LocalDatabase.databaseWriteExecutor.execute(() -> {

            long idBirraRegistrata = birraDao.insertBirra(birra);

            if(idBirraRegistrata >= 0) {
                callback.onComplete(new Risultato.Successo());
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.BIRRE_CREATION_ERROR));
            }
        });
    }

    public void updateBirra(Birra birra, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int numeroBirreModificate = birraDao.updateBirra(birra);

            if(numeroBirreModificate > 0){
                callback.onComplete(new Risultato.Successo());
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.BIRRE_UPDATE_ERROR));
            }

        });
    }

    private void createBirraAttrezzi(List<AttrezzoBirra> attrezziBirra, Callback callback) {

        LocalDatabase.databaseWriteExecutor.execute(() -> {

            long[] numeroAttrezziRegistrati = birraDao.insertBirraAttrezzi(attrezziBirra);
            boolean isOk = true;

            for (long numeroAttrezziRegistratus : numeroAttrezziRegistrati) {
                if (numeroAttrezziRegistratus < 0) {
                    isOk = false;
                }
            }

            if(isOk) {
                callback.onComplete(new Risultato.Successo());
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.BIRRE_CREATION_ERROR));
            }
        });
    }

    private void deleteBirraAttrezzi(List<AttrezzoBirra> attrezziBirra, Callback callback) {

        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int numeroBirreAttrezziCanellati = birraDao.deleteBirraAttrezzi(attrezziBirra);

            if(numeroBirreAttrezziCanellati == 0) {
                callback.onComplete(new Risultato.Errore(RegistroErrori.BIRRE_DELETE_ERROR));
            }
            else{
                callback.onComplete(new Risultato.Successo());
            }
        });
    }
}
