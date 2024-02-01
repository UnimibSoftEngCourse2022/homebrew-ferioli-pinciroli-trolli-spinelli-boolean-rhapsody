package it.unimib.brewday.database;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unimib.brewday.database.LocalDatabase;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

@RunWith(AndroidJUnit4.class)
public class LocalDatabaseTest {

    private LocalDatabase localDatabase;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        localDatabase = LocalDatabase.getDatabase(context);
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
    public void getDatabase() {
        assertNotNull(localDatabase);

        LocalDatabase secondaIstanza = LocalDatabase.getDatabase(ApplicationProvider.getApplicationContext());
        assertSame(localDatabase, secondaIstanza);
    }

    //AGGIUNGERE I TEST SUI DAO

}
