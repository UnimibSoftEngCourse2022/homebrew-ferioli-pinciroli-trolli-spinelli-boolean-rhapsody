package it.unimib.brewday.ui.gestione_birra;

import static it.unimib.brewday.util.Constants.ACQUA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.IngredienteRicetta;

public class AdapterListViewIngredientiBirra extends ArrayAdapter<IngredienteRicetta> {

    private int layout;
    private List<IngredienteRicetta> listaIngredientiRicetta;
    private List<Integer> listaIngredientiDifferenza;
    public AdapterListViewIngredientiBirra(@NonNull Context context, int resource, List<IngredienteRicetta> listaIngredientiRicetta, List<Integer> differenzaingredienti) {
        super(context, resource, listaIngredientiRicetta);
        layout = resource;
        this.listaIngredientiRicetta = listaIngredientiRicetta;
        this.listaIngredientiDifferenza = differenzaingredienti;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }
        TextView nomeIngrediente = convertView.findViewById(R.id.textView_ingrediente_birra);
        TextView unitaMisura = convertView.findViewById(R.id.textView_unitaMisura_birra);
        TextInputLayout quantitaIngrediente = convertView.findViewById(R.id.textInputLayout_quantitaIngredienteBirra);

        quantitaIngrediente.setEnabled(false);
        nomeIngrediente.setText(listaIngredientiRicetta.get(position).getTipoIngrediente().getNome());
        aggiornaVisualizzazioneIngredienti(listaIngredientiRicetta.get(position), quantitaIngrediente, unitaMisura, listaIngredientiDifferenza.get(position));


        return convertView;
    }

    private void aggiornaVisualizzazioneIngredienti(IngredienteRicetta ingredienteRicetta, TextInputLayout quantitaIngrediente, TextView unitaMisura, int differenza) {

        if (ingredienteRicetta.getTipoIngrediente().getNome().equalsIgnoreCase(ACQUA)) {
            unitaMisura.setText(" L");
            quantitaIngrediente.getEditText().setText(ingredienteRicetta.getDosaggioIngredienteToString());
            if (differenza < 0){
                quantitaIngrediente.setError("Ti mancano "+ (- differenza+ " l"));
            }
        } else {
            unitaMisura.setText(" g");
            quantitaIngrediente.getEditText().setText(Integer.toString((int) ingredienteRicetta.getDosaggioIngrediente()));
            if (differenza < 0){
                quantitaIngrediente.setError("Ti mancano "+ (- differenza)+ " g");
            }
        }
    }


}
