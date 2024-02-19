package it.unimib.brewday.util;

import android.content.Context;

import it.unimib.brewday.domain.GestioneBirre;
import it.unimib.brewday.repository.BirreRepository;
import it.unimib.brewday.repository.IngredientiRepository;
import it.unimib.brewday.repository.AttrezziRepository;
import it.unimib.brewday.database.LocalDatabase;
import it.unimib.brewday.repository.NoteDegustazioneRepository;
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

    public IngredientiRepository getIngredienteRepository(Context context){
        return  new IngredientiRepository(getRoomDatabase(context));
    }

    public AttrezziRepository getAttrezziRepository(Context context) {
        return new AttrezziRepository(getRoomDatabase(context));
    }

    public RicetteRepository getRicetteRepository(Context context){
        return new RicetteRepository(getRoomDatabase(context));
    }

    public BirreRepository getBirraRepository(Context context) {
        return new BirreRepository(getRoomDatabase(context));
    }

    public NoteDegustazioneRepository getNotaRepository(Context context) {
        return new NoteDegustazioneRepository(getRoomDatabase(context));
    }

    public LocalDatabase getRoomDatabase(Context context) {
        return LocalDatabase.getDatabase(context);
    }

    public GestioneBirre getGestioneBirraDomain(Context context) {
        return new GestioneBirre(
                getBirraRepository(context),
                getIngredienteRepository(context),
                getRicetteRepository(context),
                getAttrezziRepository(context));
    }
}