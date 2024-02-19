package it.unimib.brewday.repository;

import it.unimib.brewday.model.NotaDegustazione;
import it.unimib.brewday.ui.Callback;

public interface INoteDegustazioneRepository {

    void inserisciNotaDegustazione(NotaDegustazione notaDegustazione, Callback callback);
    void readAllNoteDegustazione( long idBirra , Callback callback);
}
