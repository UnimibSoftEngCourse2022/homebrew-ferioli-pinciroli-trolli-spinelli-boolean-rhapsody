package it.unimib.brewday.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.util.Costanti;
import it.unimib.brewday.util.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Attrezzo.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class LocalDatabase extends RoomDatabase {

    //Lista dei DAO
    public abstract AttrezzoDao attrezzoDao();

    //Istanza del DB
    private static volatile LocalDatabase INSTANCE;

    //Gestione dei thread
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Il database è un singleton
    public static LocalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class, Costanti.DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
