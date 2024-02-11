package it.unimib.brewday.repository;

import java.util.List;

import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.database.RicettaDao;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.Callback;
import it.unimib.brewday.util.RegistroErrori;

public class RicetteRepository {

    private final RicettaDao ricettaDao;

    public RicetteRepository(LocalDatabase localDatabase){
        ricettaDao = localDatabase.ricettaDao();
    }

    public void insertRicetta(Ricetta ricetta, List<IngredienteRicetta> listaDegliIngredienti, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            long id = ricettaDao.insertRicetta(ricetta);

            if(id > 0){
                for (int i = 0; i < listaDegliIngredienti.size(); i++) {
                    listaDegliIngredienti.get(i).setIdRicetta(id);
                }
                insertIngredientiRicetta(listaDegliIngredienti, callback);
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_CREATION_ERROR));
            }
        });
    }

    private void insertIngredientiRicetta(List<IngredienteRicetta> ingredienteRicetta, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            long[] id = ricettaDao.insertIngredientiRicetta(ingredienteRicetta);
            boolean isOK = true;

            for (int i = 0; i < id.length; i++) {
                if(id[i] < 0){
                    isOK = false;
                }
            }

            if(isOK){
                callback.onComplete(new Risultato.Successo());
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.INGREDIENTI_FETCH_ERROR));
            }
        });
    }

    public void getIngredientiRicetta(long idRicetta, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<IngredienteRicetta> ingredientiDellaRicetta = ricettaDao.getIngredientiRicetta(idRicetta);

            if(ingredientiDellaRicetta != null){
                callback.onComplete(new Risultato.ListaIngredientiDellaRicettaSuccesso(ingredientiDellaRicetta));
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.INGREDIENTI_FETCH_ERROR));
            }
        });
    }

    public void getRicette(Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<Ricetta> ricette = ricettaDao.getRicette();

            if(ricette != null){
                callback.onComplete(new Risultato.ListaRicetteSuccesso(ricette));
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_FETCH_ERROR));
            }
        });
    }

    public void updateRicetta(Ricetta ricetta, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            int righeAggiornate = ricettaDao.updateRicetta(ricetta);

            if(righeAggiornate > 0){
                callback.onComplete(new Risultato.Successo());
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_UPDATE_ERROR));
            }
        });
    }

    public void deleteRicetta(Ricetta ricetta, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            int righeCancellate = ricettaDao.deleteRicetta(ricetta);

            if(righeCancellate > 0){
                callback.onComplete(new Risultato.Successo());
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.RICETTA_DELETION_ERROR));
            }
        });
    }

    public void updateIngredientiRicetta(List<IngredienteRicetta> ingredienteRicetta, Callback callback) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int righeAggiornate = ricettaDao.updateIngredientiRicetta(ingredienteRicetta);

            if(righeAggiornate > 0) {
                callback.onComplete(new Risultato.Successo());
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.INGREDIENTI_UPDATE_ERROR));
            }
        });
    }
}
