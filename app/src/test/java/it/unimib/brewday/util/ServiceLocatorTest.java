package it.unimib.brewday.util;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unimib.brewday.database.LocalDatabase;

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
}