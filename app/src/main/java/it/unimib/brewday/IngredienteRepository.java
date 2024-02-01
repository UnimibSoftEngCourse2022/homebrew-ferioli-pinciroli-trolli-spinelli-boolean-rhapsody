package it.unimib.brewday;

import java.util.List;
import it.unimib.brewday.database.IngredienteDao;
import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.model.Ingrediente;

public class IngredienteRepository {

    final IngredienteDao ingredienteDao;

    public IngredienteRepository(LocalDatabase localDatabase) {
        this.ingredienteDao = localDatabase.ingredienteDao();
    }

    public void readAllIngredienti(Callback  callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<Ingrediente> listaIngredienti = ingredienteDao.getAll();
            if(listaIngredienti != null){
                callback.onComplete(new Risultato.IngredientiSuccess(listaIngredienti));
            }
            else{
                callback.onComplete(new Risultato.Error(" "));
            }
        });

    }


    public void updateAllIngredienti(List<Ingrediente> listaIngredienti, Callback  callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
           int aggiornato = ingredienteDao.updateAllIngredienti(listaIngredienti);
            if(aggiornato != 0){
                callback.onComplete(new Risultato.IngredientiSuccess(listaIngredienti));
            }
            else{
                callback.onComplete(new Risultato.Error(" "));
            }
        });

    }

    public void updateIngrediente(Ingrediente ingrediente, Callback  callback){
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            int aggiornato = ingredienteDao.updateIngrediente(ingrediente);
            if(aggiornato != 0){
                 callback.onComplete(new Risultato.IngredienteSuccess(ingrediente));
            }
            else{
                 callback.onComplete(new Risultato.Error("ingrediente non aggiornato"));
            }

        });
    }

}
