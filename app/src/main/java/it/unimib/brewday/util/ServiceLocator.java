package it.unimib.brewday.util;

import android.content.Context;

import it.unimib.brewday.repository.BirraRepository;
import it.unimib.brewday.repository.IngredienteRepository;
import it.unimib.brewday.repository.AttrezziRepository;
import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.repository.RicetteRepository;

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

    public AttrezziRepository getAttrezziRepository(Context context) {
        return new AttrezziRepository(getRoomDatabase(context));
    }

    public RicetteRepository getRicetteRepository(Context context){
        return new RicetteRepository(getRoomDatabase(context));
    }

    public BirraRepository getBirraRepository(Context context) {
        return new BirraRepository(getRoomDatabase(context));
    }

    public LocalDatabase getRoomDatabase(Context context) {
        return LocalDatabase.getDatabase(context);
    }
}