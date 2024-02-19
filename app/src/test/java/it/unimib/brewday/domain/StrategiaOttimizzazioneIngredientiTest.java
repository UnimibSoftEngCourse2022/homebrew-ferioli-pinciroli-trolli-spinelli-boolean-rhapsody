package it.unimib.brewday.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoAttrezzo;
import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.util.RegistroErrori;

public class StrategiaOttimizzazioneIngredientiTest {

    @Test
    public void testOttimizza_SenzaAttrezziDisponibili() {
        StrategiaOttimizzazioneIngredienti strategia = new StrategiaOttimizzazioneIngredienti();
        List<Attrezzo> listaAttrezziLiberi = new ArrayList<>();
        List<Ricetta> listaRicette = new ArrayList<>();
        List<IngredienteRicetta> listaIngredientiRicette = new ArrayList<>();
        List<Ingrediente> listaIngredientiDisponibili = new ArrayList<>();

        Risultato risultato = strategia.ottimizza(listaAttrezziLiberi, listaRicette, listaIngredientiRicette, listaIngredientiDisponibili);

        assertTrue(risultato instanceof Risultato.Errore);
        assertEquals(RegistroErrori.ATTREZZO_TIPOLOGIA_MANCANTE, ((Risultato.Errore) risultato).getMessaggio());
    }

    @Test
    public void testOttimizza_ConAttrezziDisponibili() {
        StrategiaOttimizzazioneIngredienti strategia = new StrategiaOttimizzazioneIngredienti();
        List<Attrezzo> listaAttrezziLiberi = new ArrayList<>();
        List<Ricetta> listaRicette = new ArrayList<>();
        List<IngredienteRicetta> listaIngredientiRicette = new ArrayList<>();
        List<Ingrediente> listaIngredientiDisponibili = new ArrayList<>();

        Attrezzo bollitorePiccolo = new Attrezzo("bollitorePiccolo", TipoAttrezzo.BOLLITORE, 3.0);
        Attrezzo fermentatorePiccolo = new Attrezzo("fermentatorePiccolo", TipoAttrezzo.FERMENTATORE, 4.0);
        Attrezzo distillatorePiccolo = new Attrezzo("distillatorePiccolo", TipoAttrezzo.DISTILLATORE, 5.0);
        listaAttrezziLiberi.add(bollitorePiccolo);
        listaAttrezziLiberi.add(fermentatorePiccolo);
        listaAttrezziLiberi.add(distillatorePiccolo);

        Ricetta ricettina = new Ricetta("Ricettina", 1);
        ricettina.setId(1);
        Ricetta ricettona = new Ricetta("Ricettona", 1);
        ricettona.setId(2);
        listaRicette.add(ricettina);
        listaRicette.add(ricettona);

        listaIngredientiRicette.add(new IngredienteRicetta(1, TipoIngrediente.MALTO, 50)); // Dosaggio in g/l
        listaIngredientiRicette.add(new IngredienteRicetta(2, TipoIngrediente.MALTO, 100));

        listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.MALTO, 200)); // Quantità in grammi

        Risultato risultato = strategia.ottimizza(listaAttrezziLiberi, listaRicette, listaIngredientiRicette, listaIngredientiDisponibili);

        assertTrue(risultato instanceof Risultato.OttimizzazioneSuccesso);
        assertNotNull(((Risultato.OttimizzazioneSuccesso) risultato).getRicetta());
        assertTrue(((Risultato.OttimizzazioneSuccesso) risultato).getLitri() > 0);
        assertEquals(2, ((Risultato.OttimizzazioneSuccesso) risultato).getRicetta().getId());
    }


}