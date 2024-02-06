package it.unimib.brewday.util;

import android.content.Context;

import it.unimib.brewday.repository.IngredienteRepository;
import it.unimib.brewday.database.LocalDatabase;

public class ServiceLocator {

    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    public IngredienteRepository getIngredienteRepository(Context context){
        return  new IngredienteRepository(getRoomDatabase(context));
    }
    public LocalDatabase getRoomDatabase(Context context) {
        return LocalDatabase.getDatabase(context);
    }
}