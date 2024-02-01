package it.unimib.brewday.util;

import android.content.Context;

import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.repository.AttrezziRepository;

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

    public AttrezziRepository getAttrezziRepository(Context context) {
        return new AttrezziRepository(getRoomDatabase(context));
    }

    public LocalDatabase getRoomDatabase(Context context) {
        return LocalDatabase.getDatabase(context);
    }
}
