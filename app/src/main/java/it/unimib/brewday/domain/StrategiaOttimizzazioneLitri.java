package it.unimib.brewday.domain;

import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.util.Ottimizzazione;
import it.unimib.brewday.util.RegistroErrori;

public class StrategiaOttimizzazioneLitri implements StrategiaOttimizzazione{

    @Override
    public Risultato ottimizza(List<Attrezzo> listaAttrezziLiberi, List<Ricetta> listaRicette, List<IngredienteRicetta> listaIngredientiRicette,
                               List<Ingrediente> listaIngredientiDisponibili) {

        int litriMassimiAttrezzi = Ottimizzazione.litriPerAttrezzi(listaAttrezziLiberi);

        if(litriMassimiAttrezzi < 0){
            return new Risultato.Errore(RegistroErrori.ATTREZZO_TIPOLOGIA_MANCANTE);
        }

        int litriPerRicettaSelezionata = -1;
        Ricetta ricettaSelezionata = null;

        for (Ricetta ricetta : listaRicette) {
            List<IngredienteRicetta> listaIngredientiDiQuestaRicetta =
                    GestioneBirreUtil.getIngredientiRicettaByIdRicetta(listaIngredientiRicette, ricetta.getId());

            int litriMassimiRicetta = Ottimizzazione.litriPerRicetta(listaIngredientiDiQuestaRicetta, listaIngredientiDisponibili);

            if(litriMassimiRicetta > litriPerRicettaSelezionata){
                litriPerRicettaSelezionata = litriMassimiRicetta;
                ricettaSelezionata = ricetta;
            }
        }

        if(litriPerRicettaSelezionata > litriMassimiAttrezzi){
            litriPerRicettaSelezionata = litriMassimiAttrezzi;
        }

        return new Risultato.OttimizzazioneSuccesso(ricettaSelezionata, litriPerRicettaSelezionata);
    }
}
