package it.unimib.brewday;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unimib.brewday.database.LocalDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class LocalDatabaseTest {

    private LocalDatabase localDatabase;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        localDatabase = Room.inMemoryDatabaseBuilder(context, LocalDatabase.class).build();
    }

    @After
    public void clean() {
        localDatabase.close();
    }

    @Test
    public void testCreazioneDatabase() {
        assertNotNull(localDatabase);
    }

    @Test
    public void testPatternSingleton() {
        LocalDatabase primaIstanza = LocalDatabase.getDatabase(ApplicationProvider.getApplicationContext());
        LocalDatabase secondaIstanza = LocalDatabase.getDatabase(ApplicationProvider.getApplicationContext());

        assertEquals(primaIstanza, secondaIstanza);
    }

    //AGGIUNGERE I TEST SUI DAO

}
