package it.unimib.brewday.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoAttrezzo;

public class Ottimizzazione {

    private Ottimizzazione() {}

    public static Risultato ottimizzaAttrezzi(List<Attrezzo> listaAttrezzi, int litriRiferimento){
        List<Attrezzo> attrezziSelezionati = selezionaAttrezzi(listaAttrezzi, litriRiferimento);

        if(TipoAttrezzo.values().length == attrezziSelezionati.size()){
            return new Risultato.AttrezziSuccesso(attrezziSelezionati);
        }
        else{
            int litriSuggeriti = suggerisciLitri(listaAttrezzi);
            if(litriSuggeriti != -1){
                return new Risultato.ErroreConSuggerimentoLitri(litriSuggeriti);
            }
            else{
                return new Risultato.Errore(RegistroErrori.ATTREZZO_TIPOLOGIA_MANCANTE);
            }

        }
    }

    private static List<Attrezzo> selezionaAttrezzi(List<Attrezzo> listaAttrezzi, int valoreRiferimento) {
        List<Attrezzo> attrezziScelti = new ArrayList<>();

        for (TipoAttrezzo tipo : TipoAttrezzo.values()) {
            Attrezzo attrezzoScelto = null;
            double differenzaMinima = Double.MAX_VALUE;

            for (Attrezzo attrezzo : listaAttrezzi) {
                if (attrezzo.getTipoAttrezzo() == tipo) {
                    double differenza = Math.abs(attrezzo.getCapacita() - valoreRiferimento);

                    if (attrezzo.getCapacita() >= valoreRiferimento && differenza < differenzaMinima) {
                        differenzaMinima = differenza;
                        attrezzoScelto = attrezzo;
                    }
                }
            }

            if (attrezzoScelto != null) {
                attrezziScelti.add(attrezzoScelto);
            }
        }

        return attrezziScelti;
    }


    private static int suggerisciLitri(List<Attrezzo> listaAttrezzi){

        boolean esisteAttrezzoPerTipologia = false;
        double litriSuggeriti = Double.MAX_VALUE;
        for (TipoAttrezzo tipo : TipoAttrezzo.values()) {

            double capacitaMassima = -1.0;

            for (Attrezzo attrezzo : listaAttrezzi) {
                if(attrezzo.getTipoAttrezzo() == tipo){
                    esisteAttrezzoPerTipologia = true;
                    if(attrezzo.getCapacita() > capacitaMassima){
                        capacitaMassima = attrezzo.getCapacita();
                    }
                }
            }

            if(!esisteAttrezzoPerTipologia){
                return -1;
            }

            if(capacitaMassima < litriSuggeriti){
                litriSuggeriti = capacitaMassima;
            }

        }

        return (int) litriSuggeriti;

    }

    private static int litriPerRicetta(
            List<IngredienteRicetta> ingredientiRicetta,
            List<Ingrediente> ingredientiPosseduti) {

        ordinaIngredientiRicettaPerTipoIngrediente(ingredientiRicetta);
        ordinaIngredientiPerTipoIngrediente(ingredientiPosseduti);
        double maxLitriDaProdurre = -1;

        for(int i = 0; i < ingredientiRicetta.size(); i++) {
            for (int j = 0; j < ingredientiPosseduti.size(); j++) {
                if(ingredientiRicetta.get(i).getTipoIngrediente().getNome().equals(ingredientiPosseduti.get(j).getTipo().getNome())) {

                    double litriIngrediente = (1 / ingredientiRicetta.get(i).getDosaggioIngrediente())
                            * ingredientiPosseduti.get(j).getQuantitaPosseduta();
                    if(maxLitriDaProdurre == -1 || litriIngrediente < maxLitriDaProdurre) {
                        maxLitriDaProdurre = litriIngrediente;
                    }
                }
            }
        }

        return Double.valueOf(maxLitriDaProdurre).intValue();
    }

    private static void ordinaIngredientiRicettaPerTipoIngrediente(List<IngredienteRicetta> ingredientiRicetta) {
        ingredientiRicetta.sort(Comparator.comparing(ingredienteRicetta -> ingredienteRicetta.getTipoIngrediente().getNome()));
    }

    private static void ordinaIngredientiPerTipoIngrediente(List<Ingrediente> listaIngredienti) {
        listaIngredienti.sort(Comparator.comparing(ingrediente -> ingrediente.getTipo().getNome()));
    }

}
