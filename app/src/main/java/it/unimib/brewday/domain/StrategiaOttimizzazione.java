package it.unimib.brewday.domain;

import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;

public interface StrategiaOttimizzazione {
    Risultato ottimizza(List<Attrezzo> listaAttrezziLiberi, List<Ricetta> listaRicette, List<IngredienteRicetta> listaIngredientiRicette,
                        List<Ingrediente> listaIngredientiDisponibili);

}
