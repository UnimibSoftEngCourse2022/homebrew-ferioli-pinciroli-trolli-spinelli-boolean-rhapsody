package it.unimib.brewday.util;

import static android.widget.Toast.LENGTH_SHORT;

import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;




import it.unimib.brewday.R;



public class GestioneRicette {

    public GestioneRicette() {
        // creazione classe
    }

    public void verificaNumeroLitriBirra(EditText numeroLitriBirra, boolean hasFocus){
        if(!hasFocus) {
            if (numeroLitriBirra.getText().toString().isEmpty()) {
                numeroLitriBirra.setText("0");
            } else {
                numeroLitriBirra.setText(String.valueOf(Integer.parseInt(numeroLitriBirra.getText().toString())));
            }
        }
    }

    public boolean controlloCreazione(View view,EditText nomeRicetta, EditText numeroLitriBirra ){
        if(nomeRicetta.getText().toString().isEmpty()){
            Snackbar.make(view, R.string.nome_ricetta_mancante, LENGTH_SHORT).show();
            return false;
        } else if( Double.parseDouble(numeroLitriBirra.getText().toString()) == 0.0 ){
            Snackbar.make(view, R.string.litri_birra_ricetta_mancante, LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
