package it.unimib.brewday.repository;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.ui.Callback;

public interface IAttrezziRepository {

    void readAllAttrezzi(Callback callback);
    void createAttrezzo(Attrezzo attrezzo, Callback callback);
    void updateAttrezzo(Attrezzo attrezzo, Callback callback);
    void deleteAttrezzo(Attrezzo attrezzo, Callback callback);
    void readAllAttrezziLiberi(Callback callback);
    void readAttrezziBirra(long idBirra, Callback callback);
}
