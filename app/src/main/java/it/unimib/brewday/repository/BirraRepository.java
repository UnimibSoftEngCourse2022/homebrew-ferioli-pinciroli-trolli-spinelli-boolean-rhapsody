package it.unimib.brewday.repository;

import java.util.List;

import it.unimib.brewday.database.BirraDao;
import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.Callback;
import it.unimib.brewday.util.RegistroErrori;

public class BirraRepository {

    private final BirraDao birraDao;

    public BirraRepository(LocalDatabase localDatabase) {
        birraDao = localDatabase.birraDao();
    }

    public void getAllBirre(Callback callback) {

        LocalDatabase.databaseWriteExecutor.execute(() -> {

            List<Birra> allBirre = birraDao.getAllBirre();

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
}
