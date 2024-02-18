package it.unimib.brewday.domain;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.TipoIngrediente;

public class GestioneBirreUtil {

    private GestioneBirreUtil(){}

    public static void calcolaDosaggiPerLitriScelti(int litriBirraScelti,
                                                    List<IngredienteRicetta> listaIngredientiRicetta ){
        for (IngredienteRicetta ingredienteRicetta : listaIngredientiRicetta) {
            if (ingredienteRicetta.getTipoIngrediente().equals(TipoIngrediente.ACQUA)) {
                ingredienteRicetta.setDosaggioIngrediente(arrotonda(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti, 1));
            } else {
                ingredienteRicetta.setDosaggioIngrediente(Math.round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti));
            }
        }
    }

    public static void calcolaDifferenzaIngredienti(List<Ingrediente> listaIngredientiDisponibili,
                                              List<IngredienteRicetta> listaIngredientiBirra,
                                              List<Integer> listaDifferenzaIngredienti){
        for(int i=0; i < listaIngredientiBirra.size(); i++){
            int differenza =  listaIngredientiDisponibili.get(i).getQuantitaPosseduta()
                    - ((int) Math.round(listaIngredientiBirra.get(i).getDosaggioIngrediente()));
            listaDifferenzaIngredienti.add(differenza);
        }
    }

    public static double arrotonda(double n, int decimals) {
        return Math.floor(n * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    public static List<IngredienteRicetta> getIngredientiRicettaByIdRicetta(List<IngredienteRicetta> ingredientiRicette, long idRicetta){
        List<IngredienteRicetta> ingredientiRicetta = new ArrayList<>();
        for (IngredienteRicetta ingrediente: ingredientiRicette) {
            if(ingrediente.getIdRicetta() == idRicetta){
                ingredientiRicetta.add(ingrediente);
            }
        }
        return ingredientiRicetta;
    }

    public static double calcolaConsumoTotale(List<IngredienteRicetta> listaIngredienti){
        double consumoTotale = 0.0;
        for (IngredienteRicetta ingrediente: listaIngredienti) {
            consumoTotale += ingrediente.getDosaggioIngrediente();
        }
        return consumoTotale;
    }
}
