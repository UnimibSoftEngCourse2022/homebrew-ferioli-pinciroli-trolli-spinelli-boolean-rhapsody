package it.unimib.brewday.ui.gestisci_ricette;

import static android.widget.Toast.LENGTH_SHORT;

import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;


import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;


public class RicetteUtil {

    private RicetteUtil() {
        // creazione classe
    }

    public static void verificaNumeroLitriBirra(EditText numeroLitriBirra, boolean hasFocus){
        if(!hasFocus) {
            if (numeroLitriBirra.getText().toString().isEmpty()) {
                numeroLitriBirra.setText("0");
            } else {
                numeroLitriBirra.setText(String.valueOf(Integer.parseInt(numeroLitriBirra.getText().toString())));
            }
        }
    }

    public static boolean controlloCreazione(View view, EditText nomeRicetta, EditText numeroLitriBirra ){
        if(nomeRicetta.getText().toString().isEmpty()){
            Snackbar.make(view, R.string.nome_ricetta_mancante, LENGTH_SHORT).show();
            return false;
        } else if(Double.parseDouble(numeroLitriBirra.getText().toString()) == 0.0 ){
            Snackbar.make(view, R.string.litri_birra_ricetta_mancante, LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static int creaListaIngredientiRicetta(List<Ingrediente> listaIngredientiRicetta, List<IngredienteRicetta> listaIngredientiRicettaPerLitro, EditText numeroLitriBirra){
            int zeroIngredienti = 0;
            double litriScelti = Double.parseDouble(numeroLitriBirra.getText().toString());
                listaIngredientiRicettaPerLitro.clear();

            for (Ingrediente ingrediente: listaIngredientiRicetta) {
                if(ingrediente.getQuantitaPosseduta() == 0) {
                    zeroIngredienti ++;
                }
                listaIngredientiRicettaPerLitro.add(new IngredienteRicetta( ingrediente.getTipo(),ingrediente.getQuantitaPosseduta() / litriScelti));
            }

            return zeroIngredienti;
    }

    public static int creaListaIngredientiRicetta(List<Ingrediente> listaIngredientiRicetta, List<IngredienteRicetta> listaIngredientiRicettaPerLitro, EditText numeroLitriBirra, long idRicetta){
        int zeroIngredienti = 0;
        double litriScelti = Double.parseDouble(numeroLitriBirra.getText().toString());
        listaIngredientiRicettaPerLitro.clear();

        for (Ingrediente ingrediente: listaIngredientiRicetta) {
            if(ingrediente.getQuantitaPosseduta() == 0) {
                zeroIngredienti ++;
            }
            listaIngredientiRicettaPerLitro.add(new IngredienteRicetta(idRicetta, ingrediente.getTipo(),ingrediente.getQuantitaPosseduta() / litriScelti));
        }

        return zeroIngredienti;
    }


}
