package it.unimib.brewday;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;




import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationBarView;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class BottomNavigationTest {
    @Rule
    public final ActivityScenarioRule<MainActivity> activityTestRule =
            new ActivityScenarioRule<>(MainActivity.class);

    private static final int[] MENU_CONTENT_ITEM_IDS = {
            R.id.listaRicetteFragment, R.id.birreFragment, R.id.creaRicettaFragment, R.id.attrezziFragment, R.id.ingredientiFragment
    };
    private Map<Integer, String> menuStringContent;

    private BottomNavigationView bottomNavigation;

    @Before
    public void setUp() {
        activityTestRule.getScenario().onActivity(activity -> {
            bottomNavigation = activity.findViewById(R.id.bottom_navigation);

            final Resources res = activity.getResources();
            menuStringContent = new HashMap<>(MENU_CONTENT_ITEM_IDS.length);
            menuStringContent.put(R.id.listaRicetteFragment, res.getString(R.string.lista_ricette));
            menuStringContent.put(R.id.birreFragment, res.getString(R.string.birre));
            menuStringContent.put(R.id.creaRicettaFragment, res.getString(R.string.crea_ricetta));
            menuStringContent.put(R.id.attrezziFragment, res.getString(R.string.attrezzi));
            menuStringContent.put(R.id.ingredientiFragment, res.getString(R.string.ingredienti));
        });



    }
    @Test

    public void testBasics() {
        //Controllo del contenuto dell'oggetto Menu
        final Menu menu = bottomNavigation.getMenu();
        assertNotNull("Menu should not be null", menu);
        assertEquals("Should have matching number of items", MENU_CONTENT_ITEM_IDS.length, menu.size());
        for (int i = 0; i < MENU_CONTENT_ITEM_IDS.length; i++) {
            final MenuItem currItem = menu.getItem(i);
            assertEquals("ID for Item #" + i, MENU_CONTENT_ITEM_IDS[i], currItem.getItemId());
        }
    }

    @Test

    public void testNavigationSelectionListener() {
        NavigationBarView.OnItemSelectedListener mockedListener = mock(NavigationBarView.OnItemSelectedListener.class);
        bottomNavigation.setOnItemSelectedListener(mockedListener);

        // true per consentire la selezione dell'elemento.
        when(mockedListener.onNavigationItemSelected(any(MenuItem.class))).thenReturn(true);
        onView(
                allOf(
                        withText(menuStringContent.get(R.id.birreFragment)),
                        isDescendantOfA(withId(R.id.bottom_navigation)),
                        isDisplayed()))
                .perform(click());

        verify(mockedListener, times(1))
                .onNavigationItemSelected(bottomNavigation.getMenu().findItem(R.id.birreFragment));
        // verifica bottone selezionato
        assertTrue(bottomNavigation.getMenu().findItem(R.id.birreFragment).isChecked());



        //  false per non consentire la selezione dell'elemento.
        when(mockedListener.onNavigationItemSelected(any(MenuItem.class))).thenReturn(false);
        onView(
                allOf(
                        withText(menuStringContent.get(R.id.creaRicettaFragment)),
                        isDescendantOfA(withId(R.id.bottom_navigation)),
                        isDisplayed()))
                .perform(click());

        verify(mockedListener, times(1))
                .onNavigationItemSelected(bottomNavigation.getMenu().findItem(R.id.creaRicettaFragment));

        assertFalse(bottomNavigation.getMenu().findItem(R.id.creaRicettaFragment).isChecked());
        assertTrue(bottomNavigation.getMenu().findItem(R.id.birreFragment).isChecked());


        // settare a null il listener per far si che permetta di selezionare gli elementi.
        bottomNavigation.setOnItemSelectedListener(null);

        onView(
                allOf(
                        withText(menuStringContent.get(R.id.listaRicetteFragment)),
                        isDescendantOfA(withId(R.id.bottom_navigation)),
                        isDisplayed()))
                .perform(click());

        assertTrue(bottomNavigation.getMenu().findItem(R.id.listaRicetteFragment).isChecked());
    }

}
