package it.unimib.brewday.util;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.domain.Ottimizzazione;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoAttrezzo;
import it.unimib.brewday.model.TipoIngrediente;

public class OttimizzazioneTest {

    @Test
    public void testOttimizzaAttrezziSuccesso() {
        List<Attrezzo> listaAttrezzi = new ArrayList<>();
        Attrezzo bollitorePiccolo = new Attrezzo("bollitorePiccolo", TipoAttrezzo.BOLLITORE, 3.0);
        Attrezzo fermentatorePiccolo = new Attrezzo("fermentatorePiccolo", TipoAttrezzo.FERMENTATORE, 4.0);
        Attrezzo distillatorePiccolo = new Attrezzo("distillatorePiccolo", TipoAttrezzo.DISTILLATORE, 5.0);
        Attrezzo bollitoreGrande = new Attrezzo("bollitoreGrande", TipoAttrezzo.BOLLITORE, 10.0);
        Attrezzo fermentatoreGrande = new Attrezzo("fermentatoreGrande", TipoAttrezzo.FERMENTATORE, 13.0);
        Attrezzo distillatoreGrande = new Attrezzo("distillatoreGrande", TipoAttrezzo.DISTILLATORE, 17.0);
        listaAttrezzi.add(bollitorePiccolo);
        listaAttrezzi.add(fermentatorePiccolo);
        listaAttrezzi.add(distillatorePiccolo);
        listaAttrezzi.add(bollitoreGrande);
        listaAttrezzi.add(fermentatoreGrande);
        listaAttrezzi.add(distillatoreGrande);

        Risultato litriBassi = Ottimizzazione.ottimizzaAttrezzi(listaAttrezzi, 2);
        assertTrue(litriBassi instanceof Risultato.ListaAttrezziSuccesso);
        assertEquals(TipoAttrezzo.values().length, ((Risultato.ListaAttrezziSuccesso) litriBassi).getAttrezzi().size());
        assertTrue(((Risultato.ListaAttrezziSuccesso) litriBassi).getAttrezzi().contains(bollitorePiccolo));
        assertTrue(((Risultato.ListaAttrezziSuccesso) litriBassi).getAttrezzi().contains(fermentatorePiccolo));
        assertTrue(((Risultato.ListaAttrezziSuccesso) litriBassi).getAttrezzi().contains(distillatorePiccolo));

        Risultato litriMedi = Ottimizzazione.ottimizzaAttrezzi(listaAttrezzi, 4);
        assertTrue(litriMedi instanceof Risultato.ListaAttrezziSuccesso);
        assertEquals(TipoAttrezzo.values().length, ((Risultato.ListaAttrezziSuccesso) litriMedi).getAttrezzi().size());
        assertTrue(((Risultato.ListaAttrezziSuccesso) litriMedi).getAttrezzi().contains(bollitoreGrande));
        assertTrue(((Risultato.ListaAttrezziSuccesso) litriMedi).getAttrezzi().contains(fermentatorePiccolo));
        assertTrue(((Risultato.ListaAttrezziSuccesso) litriMedi).getAttrezzi().contains(distillatorePiccolo));

        Risultato litriAlti = Ottimizzazione.ottimizzaAttrezzi(listaAttrezzi, 6);
        assertTrue(litriAlti instanceof Risultato.ListaAttrezziSuccesso);
        assertEquals(TipoAttrezzo.values().length, ((Risultato.ListaAttrezziSuccesso) litriAlti).getAttrezzi().size());
        assertTrue(((Risultato.ListaAttrezziSuccesso) litriAlti).getAttrezzi().contains(bollitoreGrande));
        assertTrue(((Risultato.ListaAttrezziSuccesso) litriAlti).getAttrezzi().contains(fermentatoreGrande));
        assertTrue(((Risultato.ListaAttrezziSuccesso) litriAlti).getAttrezzi().contains(distillatoreGrande));
    }

    @Test
    public void testOttimizzaAttrezziErroreConSuggerimentoLitri() {
        List<Attrezzo> listaAttrezzi = new ArrayList<>();
        Attrezzo bollitorePiccolo = new Attrezzo("bollitorePiccolo", TipoAttrezzo.BOLLITORE, 3.0);
        Attrezzo fermentatorePiccolo = new Attrezzo("fermentatorePiccolo", TipoAttrezzo.FERMENTATORE, 4.0);
        Attrezzo distillatorePiccolo = new Attrezzo("distillatorePiccolo", TipoAttrezzo.DISTILLATORE, 5.0);
        listaAttrezzi.add(bollitorePiccolo);
        listaAttrezzi.add(fermentatorePiccolo);
        listaAttrezzi.add(distillatorePiccolo);


        Risultato risultato = Ottimizzazione.ottimizzaAttrezzi(listaAttrezzi, 10);
        assertTrue(risultato instanceof Risultato.ErroreConSuggerimentoLitri);
        assertEquals(3, ((Risultato.ErroreConSuggerimentoLitri) risultato).getLitriSuggeriti());

    }

    @Test
    public void testOttimizzaAttrezziErrore() {
        List<Attrezzo> listaAttrezzi = new ArrayList<>();

        Risultato risultato = Ottimizzazione.ottimizzaAttrezzi(listaAttrezzi, 10);
        assertTrue(risultato instanceof Risultato.Errore);
        assertEquals(RegistroErrori.ATTREZZO_TIPOLOGIA_MANCANTE, ((Risultato.Errore) risultato).getMessaggio());
    }

    @Test
    public void testLitriPerAttrezziConAttrezzi() {
        List<Attrezzo> listaAttrezzi = new ArrayList<>();
        Attrezzo bollitorePiccolo = new Attrezzo("bollitorePiccolo", TipoAttrezzo.BOLLITORE, 3.0);
        Attrezzo fermentatorePiccolo = new Attrezzo("fermentatorePiccolo", TipoAttrezzo.FERMENTATORE, 4.0);
        Attrezzo distillatorePiccolo = new Attrezzo("distillatorePiccolo", TipoAttrezzo.DISTILLATORE, 5.0);
        listaAttrezzi.add(bollitorePiccolo);
        listaAttrezzi.add(fermentatorePiccolo);
        listaAttrezzi.add(distillatorePiccolo);

        int litriSuggeriti = Ottimizzazione.litriPerAttrezzi(listaAttrezzi);
        assertEquals(3, litriSuggeriti);
    }

    @Test
    public void testLitriPerAttrezziSenzaAttrezzi() {
        List<Attrezzo> listaAttrezzi = new ArrayList<>();

        int litriSuggeriti = Ottimizzazione.litriPerAttrezzi(listaAttrezzi);
        assertEquals(-1, litriSuggeriti);
    }

    @Test
    public void testLitriPerRicettaConIngredientiPosseduti() {
        List<IngredienteRicetta> ingredientiRicetta = new ArrayList<>();
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.ACQUA, 50));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.ADDITIVI, 70));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.LIEVITI, 20));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.LUPPOLO, 100));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.MALTO, 30));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.ZUCCHERO, 45));


        List<Ingrediente> ingredientiPosseduti = new ArrayList<>();
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.ACQUA, 1000));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.ADDITIVI, 1000));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.LIEVITI, 1000));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.LUPPOLO, 1000));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.MALTO, 1000));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.ZUCCHERO, 1000));

        int litriPerRicetta = Ottimizzazione.litriPerRicetta(ingredientiRicetta, ingredientiPosseduti);
        assertEquals(10, litriPerRicetta);

    }

    @Test
    public void testLitriPerRicettaSenzaIngredientiPosseduti() {
        List<IngredienteRicetta> ingredientiRicetta = new ArrayList<>();
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.ACQUA, 50));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.ADDITIVI, 70));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.LIEVITI, 20));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.LUPPOLO, 100));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.MALTO, 30));
        ingredientiRicetta.add(new IngredienteRicetta(1, TipoIngrediente.ZUCCHERO, 45));


        List<Ingrediente> ingredientiPosseduti = new ArrayList<>();
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.ACQUA, 0));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.ADDITIVI, 0));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.LIEVITI, 0));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.LUPPOLO, 0));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.MALTO, 0));
        ingredientiPosseduti.add(new Ingrediente(TipoIngrediente.ZUCCHERO, 0));

        int litriPerRicetta = Ottimizzazione.litriPerRicetta(ingredientiRicetta, ingredientiPosseduti);
        assertEquals(0, litriPerRicetta);

    }
}