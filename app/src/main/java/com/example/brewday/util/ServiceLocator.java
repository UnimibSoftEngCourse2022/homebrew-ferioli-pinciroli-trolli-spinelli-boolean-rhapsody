package com.example.brewday.util;

import android.content.Context;

import com.example.brewday.database.LocalDatabase;
import com.example.brewday.repository.AttrezziRepository;

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
