package it.unimib.brewday.repository;

import java.util.List;

import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.database.NotaDegustazioneDao;
import it.unimib.brewday.model.NotaDegustazione;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.Callback;
import it.unimib.brewday.util.RegistroErrori;

public class NoteDegustazioneRepository {
    private final NotaDegustazioneDao notaDegustazioneDao;


    public NoteDegustazioneRepository(LocalDatabase localDatabase) {
        this.notaDegustazioneDao = localDatabase.notaDegustazioneDao();
    }





    public void inserisciNotaDegustazione(NotaDegustazione notaDegustazione, Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            long numeroNoteInseriti = notaDegustazioneDao.inserisciNota(notaDegustazione);
            if (numeroNoteInseriti > 0) {
                callback.onComplete(new Risultato.Successo());
            } else {
                callback.onComplete(new Risultato.Errore(RegistroErrori.NOTA_CREATION_ERROR));

            }


        });
        }
        public void readAllNoteDegustazione( long idBirra , Callback callback){
            LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<NotaDegustazione> listaNoteDegustazione = notaDegustazioneDao.getListaNoteDegustazione(idBirra);
                if (listaNoteDegustazione != null) {
                    callback.onComplete(new Risultato.AllNoteDegustazioneSuccesso(listaNoteDegustazione));
                } else {
                    callback.onComplete(new Risultato.Errore(RegistroErrori.NOTA_FETCH_ERROR));

                }

            });
        }

        public void readMediaNotaDegustazione(long idBirra, Callback callback){
            LocalDatabase.databaseWriteExecutor.execute(() -> {
                Float media = notaDegustazioneDao.mediaNoteDegustazione(idBirra);
                if (media != null) {
                    callback.onComplete(new Risultato.MediaNotaDegustazioneSuccesso(media));
                } else {
                    callback.onComplete(new Risultato.Errore(RegistroErrori.NOTA_MEDIA_ERROR));

                }


            });
        }

}
