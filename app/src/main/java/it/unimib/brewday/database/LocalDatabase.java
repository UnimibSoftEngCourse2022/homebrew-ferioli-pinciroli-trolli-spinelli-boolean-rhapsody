package it.unimib.brewday.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.util.Converters;
import it.unimib.brewday.util.Costanti;
import it.unimib.brewday.util.ListaIngredienti;

@Database(entities = {Attrezzo.class, Ingrediente.class, Ricetta.class, IngredienteRicetta.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class LocalDatabase extends RoomDatabase {



    //Lista dei DAO
    public abstract IngredienteDao ingredienteDao();
    public abstract AttrezzoDao attrezzoDao();
    public abstract RicettaDao ricettaDao();

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
                        LocalDatabase.class, Costanti.NOME_DATABASE
                )

                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        final IngredienteDao dao = INSTANCE.ingredienteDao();
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