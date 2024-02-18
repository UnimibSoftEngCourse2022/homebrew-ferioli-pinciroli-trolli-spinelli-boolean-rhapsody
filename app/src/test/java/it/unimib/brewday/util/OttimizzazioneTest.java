package it.unimib.brewday.util;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoAttrezzo;

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
    public void testLitriPerRicetta() {
        List<IngredienteRicetta> ingredientiRicetta = new ArrayList<>();
        // Aggiungi ingredienti alla ricetta
        // ...

        List<Ingrediente> ingredientiPosseduti = new ArrayList<>();
        // Aggiungi ingredienti posseduti
        // ...

        int litriPerRicetta = Ottimizzazione.litriPerRicetta(ingredientiRicetta, ingredientiPosseduti);
        assertNotEquals(-1, litriPerRicetta);
        // Aggiungi ulteriori asserzioni se necessario
    }
}