package it.unimib.brewday.util;

import android.content.Context;
import it.unimib.brewday.database.LocalDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class ServiceLocatorTest {

    private ServiceLocator serviceLocator;

    @Before
    public void setUp() {
        serviceLocator = ServiceLocator.getInstance();
    }

    @After
    public void clean() {
        serviceLocator = null;
    }

    @Test
    public void getInstance() {
        ServiceLocator secondInstance = ServiceLocator.getInstance();
        assertSame(serviceLocator, secondInstance);
    }

    @Test
    public void getRoomDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        LocalDatabase localDatabase = serviceLocator.getRoomDatabase(context);
        assertNotNull(localDatabase);
    }
}
