package it.unimib.brewday.domain;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoAttrezzo;
import it.unimib.brewday.util.RegistroErrori;

public class Ottimizzazione {

    private Ottimizzazione() {}

    public static Risultato ottimizzaAttrezzi(List<Attrezzo> listaAttrezzi, int litriRiferimento){
        List<Attrezzo> attrezziSelezionati = selezionaAttrezzi(listaAttrezzi, litriRiferimento);

        if(TipoAttrezzo.values().length == attrezziSelezionati.size()){
            return new Risultato.ListaAttrezziSuccesso(attrezziSelezionati);
        }
        else{
            int litriSuggeriti = litriPerAttrezzi(listaAttrezzi);
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

    //calcola il numero massimo di litri che è possibile produrre con gli attrezzi passati
    public static int litriPerAttrezzi(List<Attrezzo> listaAttrezzi){

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


    public static int litriPerRicetta(List<IngredienteRicetta> ingredientiRicetta, List<Ingrediente> ingredientiPosseduti) {

        double maxLitriDaProdurre = -1;

        for (IngredienteRicetta ingredienteDellaRicetta : ingredientiRicetta) {
            for (Ingrediente ingrediente : ingredientiPosseduti) {
                if(ingredienteDellaRicetta.getTipoIngrediente().getNome().equals(ingrediente.getTipo().getNome())){
                    double litriIngrediente = (1 / ingredienteDellaRicetta.getDosaggioIngrediente()) * ingrediente.getQuantitaPosseduta();
                    if(maxLitriDaProdurre < 0 || litriIngrediente < maxLitriDaProdurre) {
                        maxLitriDaProdurre = litriIngrediente;
                    }
                }
            }
        }

        return (int) maxLitriDaProdurre;
    }



}
