package it.unimib.brewday.repository;

import java.util.List;

import it.unimib.brewday.model.AttrezzoBirra;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.ui.Callback;

public interface IBirreRepository {

    void readAllBirre(Callback callback);
    void createBirra(Birra birra, List<AttrezzoBirra> attrezziBirra, Callback callback);
    void terminaBirra(Birra birra, Callback callback);
    void updateBirra(Birra birra, Callback callback);
}
