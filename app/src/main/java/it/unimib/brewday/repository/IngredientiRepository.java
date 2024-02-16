package it.unimib.brewday.repository;

import java.util.List;

import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.database.IngredienteDao;
import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.ui.Callback;

public class IngredientiRepository {

    final IngredienteDao ingredienteDao;

    public IngredientiRepository(LocalDatabase localDatabase) {
        this.ingredienteDao = localDatabase.ingredienteDao();
    }

    public void readAllIngredienti(Callback callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<Ingrediente> listaIngredienti = ingredienteDao.getAllIngredienti();
            if(listaIngredienti != null){
                callback.onComplete(new Risultato.ListaIngredientiSuccesso(listaIngredienti));
            }
            else{
                callback.onComplete(new Risultato.Errore("Fallimento lettura ingredienti disponibili"));
            }
        });

    }


    public void updateAllIngredienti(List<Ingrediente> listaIngredienti, Callback  callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
           int aggiornato = ingredienteDao.updateAllIngredienti(listaIngredienti);
            if(aggiornato != 0){
                callback.onComplete(new Risultato.ListaIngredientiSuccesso(listaIngredienti));
            }
            else{
                callback.onComplete(new Risultato.Errore("ingredienti non aggiornati "));
            }
        });

    }

    public void updateIngrediente(Ingrediente ingrediente, Callback  callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            int aggiornato = ingredienteDao.updateIngrediente(ingrediente);
            if(aggiornato != 0){
                 callback.onComplete(new Risultato.IngredienteSuccesso(ingrediente));
            }
            else{
                 callback.onComplete(new Risultato.Errore("ingrediente non aggiornato"));
            }

        });
    }

}
