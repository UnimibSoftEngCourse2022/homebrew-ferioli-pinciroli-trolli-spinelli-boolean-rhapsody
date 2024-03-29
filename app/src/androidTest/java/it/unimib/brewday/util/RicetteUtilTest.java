package it.unimib.brewday.util;

import android.content.Context;
import android.widget.EditText;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.ui.gestisci_ricette.RicetteUtil;

public class RicetteUtilTest {

    private List<Ingrediente> listaIngredientiRicetta;
    private List<IngredienteRicetta> listaIngredientiPerLitro;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        listaIngredientiRicetta = new ArrayList<>();
        listaIngredientiPerLitro = new ArrayList<>();
    }

    @Test
    public void testVerificaNumeroLitriBirra() {
        // Test quando hasFocus è falso
        EditText editText = new EditText(context);
        editText.setText("");
        RicetteUtil.verificaNumeroLitriBirra(editText, false);
        assertEquals("0", editText.getText().toString());

        // Test quando hasFocus è vero
        editText.setText("10");
        RicetteUtil.verificaNumeroLitriBirra(editText, true);
        assertEquals("10", editText.getText().toString());
    }



    @Test
    public void testControlloCreazione() {
        EditText nomeRicetta = new EditText(context);
        EditText numeroLitriBirra = new EditText(context);

        // Test quando numeroLitriBirra è zero
        nomeRicetta.setText("Ricetta1");
        numeroLitriBirra.setText("10");
        assertTrue(RicetteUtil.controlloCreazione(null, nomeRicetta, numeroLitriBirra));
    }


    @Test
    public void testCreaListaIngredientiRicetta() {
        // Prepara i dati di test
        Ingrediente ingrediente1 = new Ingrediente(TipoIngrediente.ACQUA, 100);
        Ingrediente ingrediente2 = new Ingrediente(TipoIngrediente.MALTO, 200);
        listaIngredientiRicetta.add(ingrediente1);
        listaIngredientiRicetta.add(ingrediente2);

        EditText numeroLitriBirra = new EditText(context);
        numeroLitriBirra.setText("5");

        // Metodo di test
        int zeroIngredienti = RicetteUtil.creaListaIngredientiRicetta(listaIngredientiRicetta, listaIngredientiPerLitro, numeroLitriBirra);

        // Verifica se il calcolo degli ingredienti per litro è corretto
        assertEquals(2, listaIngredientiPerLitro.size());
        assertEquals(20.0, listaIngredientiPerLitro.get(0).getDosaggioIngrediente(), 0);
        assertEquals(40.0, listaIngredientiPerLitro.get(1).getDosaggioIngrediente(), 0);

        // Verifica se il numero di ingredienti con quantità zero è corretto
        assertEquals(0, zeroIngredienti);
    }
}

