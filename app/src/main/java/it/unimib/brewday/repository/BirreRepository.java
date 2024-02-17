package it.unimib.brewday.repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

    public void createBirra(Birra birra, List<AttrezzoBirra> attrezziBirra, Callback callback) {

        LocalDatabase.databaseWriteExecutor.execute(() -> {

            long idBirraRegistrata = birraDao.insertBirra(birra);

            if(idBirraRegistrata >= 0) {
                for (AttrezzoBirra attrezzo: attrezziBirra) {
                    attrezzo.setIdBirra(idBirraRegistrata);
                }
                this.createBirraAttrezzi(attrezziBirra, callback);
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.BIRRE_CREATION_ERROR));
            }
        });
    }

    public void terminaBirra(Birra birra, Callback callback){
        birra.setTerminata(true);
        birra.setDataTerminazione(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                .format(Calendar.getInstance().getTime()));
        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int numeroBirreModificate = birraDao.updateBirra(birra);

            if(numeroBirreModificate > 0){
                deleteBirraAttrezzi(birra.getId(), callback);
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

    private void deleteBirraAttrezzi(long idBirra, Callback callback) {

        LocalDatabase.databaseWriteExecutor.execute(() -> {

            int numeroBirreAttrezziCancellati = birraDao.deleteBirraAttrezzi(idBirra);

            if(numeroBirreAttrezziCancellati > 0) {
                callback.onComplete(new Risultato.Successo());
            }
            else{
                callback.onComplete(new Risultato.Errore(RegistroErrori.BIRRE_DELETE_ERROR));

            }
        });
    }



}
