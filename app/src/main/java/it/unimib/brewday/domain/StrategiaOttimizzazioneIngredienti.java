package it.unimib.brewday.domain;

import java.util.List;

import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.ui.Callback;
import it.unimib.brewday.util.Ottimizzazione;

public class StrategiaOttimizzazioneIngredienti implements StrategiaOttimizzazione{



    @Override
    public void ottimizza(Callback callback) {
/*        double consumoMassimo = -1.0;
        int litriPerRicettaSelezionata = 0;
        Ricetta ricettaSelezionata = null;

        for (Ricetta ricetta : listaRicette) {
            List<IngredienteRicetta> listaIngredientiDiQuestaRicetta =
                    getIngredientiRicettaByIdRicetta(listaIngredientiRicette, ricetta.getId());

            int litriMassimiPerRicetta = Ottimizzazione.litriPerRicetta(listaIngredientiDiQuestaRicetta, listaIngredientiDisponibili);

            if(litriMassimiPerRicetta < litriMassimiAttrezzi){
                setDosaggioDaIngredienteRicetta(litriMassimiPerRicetta, listaIngredientiDiQuestaRicetta);
                double consumoTotale = calcolaConsumoTotale(listaIngredientiDiQuestaRicetta);

                if(consumoTotale > consumoMassimo){
                    consumoMassimo = consumoTotale;
                    litriPerRicettaSelezionata = litriMassimiPerRicetta;
                    ricettaSelezionata = ricetta;
                }
            }
            else{
                setDosaggioDaIngredienteRicetta(litriMassimiAttrezzi, listaIngredientiDiQuestaRicetta);
                double consumoTotale = calcolaConsumoTotale(listaIngredientiDiQuestaRicetta);

                if(consumoTotale > consumoMassimo){
                    consumoMassimo = consumoTotale;
                    litriPerRicettaSelezionata = litriMassimiAttrezzi;
                    ricettaSelezionata = ricetta;
                }
            }

        }*/
    }
}
