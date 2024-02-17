package it.unimib.brewday.domain;

import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.ui.Callback;

public interface IGestioneBirraDomain {

    void getAllBirre(Callback callback);

    void createBirra(Birra birra,
                     List<Integer> listaDifferenzaIngredienti,
                     List<Attrezzo> listaAttrezzi,
                     Callback callback);

    void terminaBirra(Birra birra, Callback callback);

    void getIngredientiRicettaBirra(long idRicetta, int litriBirraScelti, Callback callback);

    void getDifferenzaIngredientiRicettaBirra(List<IngredienteRicetta> ingredientiRicetta, Callback callback);

    void getAndOptimizeAttrezziLiberi(int litriScelti, Callback callback);
}
