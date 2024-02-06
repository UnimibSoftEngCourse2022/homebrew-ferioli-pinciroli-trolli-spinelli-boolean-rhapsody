package it.unimib.brewday.repository;

import java.util.List;

import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.database.RicettaDao;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.Callback;
import it.unimib.brewday.util.RegistroErrori;

public class RicetteRepository {

    private final RicettaDao ricettaDao;

    public RicetteRepository(LocalDatabase localDatabase){
        ricettaDao = localDatabase.ricettaDao();
    }

    public void readListaRicette(Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<Ricetta> allRicette = ricettaDao.getAllRicette();

            if(allRicette != null) {
                callback.onComplete(new Risultato.ListaRicetteSuccesso(allRicette));
            }
            else {
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_FETCH_ERROR));
            }
        });
    }

    public void readRicettaByName(String nomeRicetta, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<Ricetta> ricetteTrovate = ricettaDao.getRicettaByName(nomeRicetta);

            if(ricetteTrovate != null){
                callback.onComplete(new Risultato.ListaRicetteSuccesso(ricetteTrovate));
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_FETCH_ERROR));
            }
        });
    }

    public void readRicettaById(long id, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<Ricetta> ricetteTrovate = ricettaDao.getRicettaById(id);

            if(ricetteTrovate != null){
                callback.onComplete(new Risultato.SingolaRicettaSuccesso(ricetteTrovate.get(0)));
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_FETCH_ERROR));
            }
        });
    }

    public void createRicetta(Ricetta ricetta, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            long id = ricettaDao.insertRicetta(ricetta);

            if(id < 0) {
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_CREATION_ERROR));
            }
            else{
                callback.onComplete(new Risultato.Successo());
            }
        });
    }

    public void updateRicetta(Ricetta ricetta, Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int rowsUpdated = ricettaDao.updateRicetta(ricetta);
            if (rowsUpdated == 0) {
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_UPDATE_ERROR));
            }
            else{
                callback.onComplete(new Risultato.Successo());
            }
        });
    }

    public void deleteRicetta(Ricetta ricetta, Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int rowsDeleted = ricettaDao.deleteRicetta(ricetta);
            if (rowsDeleted == 0) {
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_DELETION_ERROR));
            }
            else{
                callback.onComplete(new Risultato.Successo());
            }
        });
    }
}
