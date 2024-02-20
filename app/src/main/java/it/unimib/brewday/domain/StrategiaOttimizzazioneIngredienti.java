package it.unimib.brewday.domain;

import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.util.RegistroErrori;

public class StrategiaOttimizzazioneIngredienti implements StrategiaOttimizzazione{



    @Override
    public Risultato ottimizza(List<Attrezzo> listaAttrezziLiberi, List<Ricetta> listaRicette, List<IngredienteRicetta> listaIngredientiRicette,
                               List<Ingrediente> listaIngredientiDisponibili) {

        int litriMassimiAttrezzi = Ottimizzazione.litriPerAttrezzi(listaAttrezziLiberi);

        if(litriMassimiAttrezzi < 0){
            return new Risultato.Errore(RegistroErrori.ATTREZZO_TIPOLOGIA_MANCANTE);
        }


        double consumoMassimo = 0;
        int litriPerRicettaSelezionata = 0;
        Ricetta ricettaSelezionata = null;

        for (Ricetta ricetta : listaRicette) {
            List<IngredienteRicetta> listaIngredientiDiQuestaRicetta =
                    GestioneBirreUtil.getIngredientiRicettaByIdRicetta(listaIngredientiRicette, ricetta.getId());

            int litriMassimiPerRicetta = Ottimizzazione.litriPerRicetta(listaIngredientiDiQuestaRicetta, listaIngredientiDisponibili);

            if(litriMassimiPerRicetta < litriMassimiAttrezzi){
                GestioneBirreUtil.calcolaDosaggiPerLitriScelti(litriMassimiPerRicetta, listaIngredientiDiQuestaRicetta);
                double consumoTotale = GestioneBirreUtil.calcolaConsumoTotale(listaIngredientiDiQuestaRicetta);

                if(consumoTotale > consumoMassimo){
                    consumoMassimo = consumoTotale;
                    litriPerRicettaSelezionata = litriMassimiPerRicetta;
                    ricettaSelezionata = ricetta;
                }
            }
            else{
                GestioneBirreUtil.calcolaDosaggiPerLitriScelti(litriMassimiAttrezzi, listaIngredientiDiQuestaRicetta);
                double consumoTotale = GestioneBirreUtil.calcolaConsumoTotale(listaIngredientiDiQuestaRicetta);

                if(consumoTotale > consumoMassimo){
                    consumoMassimo = consumoTotale;
                    litriPerRicettaSelezionata = litriMassimiAttrezzi;
                    ricettaSelezionata = ricetta;
                }
            }

        }

        return new Risultato.OttimizzazioneSuccesso(ricettaSelezionata, litriPerRicettaSelezionata);
    }
}
