package it.unimib.brewday.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.util.ListaIngredienti;

@Database(entities = {Attrezzo.class, Ingrediente.class, Ricetta.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {



    //Lista dei DAO
    public abstract IngredienteDao ingredienteDao();

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
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }


    private static LocalDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(
                        context.getApplicationContext(),
                        LocalDatabase.class, "DatabaseIngredienti"
                )
                // prepopulate the database after onCreate was called
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        final IngredienteDao dao = INSTANCE.ingredienteDao();
                        // insert the data on the IO Thread
                        Executors.newSingleThreadExecutor().execute(() ->
                                popolaIngredienteDB(dao, new ListaIngredienti().getListaIngredienti() ));
                    }

                    private void popolaIngredienteDB(IngredienteDao dao, List<Ingrediente> listaIngredienti) {
                        dao.insertIngredientiList(listaIngredienti);
                    }
                })
                .build();
    }
}