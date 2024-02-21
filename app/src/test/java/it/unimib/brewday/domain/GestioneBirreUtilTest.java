package it.unimib.brewday.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.TipoIngrediente;

public class GestioneBirreUtilTest {

    @Test
    public void testCalcolaDosaggiPerLitriScelti() {
        List<IngredienteRicetta> listaIngredientiRicetta = new ArrayList<>();
        listaIngredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.MALTO, 0.5));

        GestioneBirreUtil.calcolaDosaggiPerLitriScelti(10, listaIngredientiRicetta);
        assertEquals(5.0, listaIngredientiRicetta.get(0).getDosaggioIngrediente(), 0.01);
    }

    @Test
    public void testCalcolaDifferenzaIngredienti() {
        List<Ingrediente> listaIngredientiDisponibili = new ArrayList<>();
        List<IngredienteRicetta> listaIngredientiBirra = new ArrayList<>();

        listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.MALTO, 200));
        listaIngredientiBirra.add(new IngredienteRicetta(1, TipoIngrediente.MALTO, 50));

        List<Integer> listaDifferenzaIngredienti = new ArrayList<>();
        GestioneBirreUtil.calcolaDifferenzaIngredienti(listaIngredientiDisponibili, listaIngredientiBirra, listaDifferenzaIngredienti);
        assertEquals(150, listaDifferenzaIngredienti.get(0).intValue());
    }

    @Test
    public void testArrotonda() {
        assertEquals(2.34, GestioneBirreUtil.arrotonda(2.345, 2), 0.01);
    }

    @Test
    public void testGetIngredientiRicettaByIdRicetta() {
        List<IngredienteRicetta> ingredientiRicette = new ArrayList<>();
        ingredientiRicette.add(new IngredienteRicetta(1, TipoIngrediente.MALTO, 50));
        ingredientiRicette.add(new IngredienteRicetta(1, TipoIngrediente.LIEVITI, 20));
        ingredientiRicette.add(new IngredienteRicetta(2, TipoIngrediente.MALTO, 80));

        List<IngredienteRicetta> risultato = GestioneBirreUtil.getIngredientiRicettaByIdRicetta(ingredientiRicette, 1);
        assertEquals(2, risultato.size());
    }

    @Test
    public void testCalcolaConsumoTotale() {
        List<IngredienteRicetta> listaIngredienti = new ArrayList<>();
        listaIngredienti.add(new IngredienteRicetta(1, TipoIngrediente.MALTO, 50));
        listaIngredienti.add(new IngredienteRicetta(1, TipoIngrediente.LIEVITI, 20));

        assertEquals(70.0, GestioneBirreUtil.calcolaConsumoTotale(listaIngredienti), 0.01);
    }
}