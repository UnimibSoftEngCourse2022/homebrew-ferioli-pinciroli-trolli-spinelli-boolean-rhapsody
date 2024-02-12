package it.unimib.brewday.ui.gestione_birra;

import static it.unimib.brewday.util.Constants.ACQUA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;

public class AdapterListViewIngredientiBirra extends ArrayAdapter<IngredienteRicetta> {

    private int layout;
    private List<IngredienteRicetta> listaIngredientiRicetta;
    public AdapterListViewIngredientiBirra(@NonNull Context context, int resource, List<IngredienteRicetta> listaIngredientiRicetta) {
        super(context, resource, listaIngredientiRicetta);
        layout = resource;
        this.listaIngredientiRicetta = listaIngredientiRicetta;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }
        TextView nomeIngrediente = convertView.findViewById(R.id.textView_ingrediente_birra);
        TextView unitaMisura = convertView.findViewById(R.id.textView_unitaMisura_birra);
        TextView quantitaIngrediente = convertView.findViewById(R.id.textView_quantitaIngredienteBirra);

        nomeIngrediente.setText(listaIngredientiRicetta.get(position).getTipoIngrediente().getNome());
        aggiornaVisualizzazioneIngredienti(listaIngredientiRicetta.get(position), quantitaIngrediente, unitaMisura);

        return convertView;
    }

    private void aggiornaVisualizzazioneIngredienti(IngredienteRicetta ingredienteRicetta, TextView quantitaIngrediente, TextView unitaMisura) {

        if (ingredienteRicetta.getTipoIngrediente().getNome().equalsIgnoreCase(ACQUA)) {
            unitaMisura.setText(" L");
            quantitaIngrediente.setText(ingredienteRicetta.getDosaggioIngredienteToString());
        } else {
            unitaMisura.setText(" g");
            quantitaIngrediente.setText(Integer.toString((int) ingredienteRicetta.getDosaggioIngrediente()));
        }
    }
}
