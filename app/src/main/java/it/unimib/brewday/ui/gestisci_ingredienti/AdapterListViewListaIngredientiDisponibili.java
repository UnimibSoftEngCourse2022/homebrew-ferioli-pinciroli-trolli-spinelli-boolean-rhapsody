package it.unimib.brewday.ui.gestisci_ingredienti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ingrediente;

public class AdapterListViewListaIngredientiDisponibili extends ArrayAdapter<Ingrediente> {


    private final List<Ingrediente> listaIngredienti;


    private final int layout;

    private final OnItemClickListener onItemClickListener;

    private final OnFocusChangeListener onFocusChangeListener;

    public AdapterListViewListaIngredientiDisponibili(@NonNull Context context, int resource, List<Ingrediente> listaIngredienti, int layout, OnItemClickListener onItemClickListener, OnFocusChangeListener onFocusChangeListener) {
        super(context, resource, listaIngredienti);
        this.listaIngredienti = listaIngredienti;
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
        this.onFocusChangeListener = onFocusChangeListener;
    }

    public interface OnItemClickListener {

        void onAddIngredienteClick(Ingrediente ingrediente, int position);

        void onRemoveIngredienteClick(Ingrediente ingrediente, int position);

    }

    public interface OnFocusChangeListener {

        void onChangeIngrediente(Ingrediente ingrediente, EditText quantitaIngrediente, int position);
    }
    @Override
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }
        TextView nomeIngrediente = convertView.findViewById(R.id.textView_ingrediente);
        TextView unitaMisura = convertView.findViewById(R.id.textView_unitaMisura);
        EditText quantitaIngrediente = convertView.findViewById(R.id.editTextText_ingrediente);
        FloatingActionButton aggiungiIngrediente = convertView.findViewById(R.id.button_aggiungi_ingrediente);
        FloatingActionButton rimuoviIngrediente = convertView.findViewById(R.id.button_rimuovi_ingrediente);



        aggiungiIngrediente.setOnClickListener(v -> {
            onItemClickListener.onAddIngredienteClick(listaIngredienti.get(position), position);
            aggiornaVisualizzazioneIngredienti(listaIngredienti.get(position), quantitaIngrediente, unitaMisura);
        });
        rimuoviIngrediente.setOnClickListener(v -> {
            onItemClickListener.onRemoveIngredienteClick(listaIngredienti.get(position), position);
            aggiornaVisualizzazioneIngredienti(listaIngredienti.get(position), quantitaIngrediente, unitaMisura);
        });

        quantitaIngrediente.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus)
               onFocusChangeListener.onChangeIngrediente(listaIngredienti.get(position), quantitaIngrediente, position);
        });



        nomeIngrediente.setText(listaIngredienti.get(position).getTipo().getNome());
        aggiornaVisualizzazioneIngredienti(listaIngredienti.get(position), quantitaIngrediente, unitaMisura);
        return convertView;
    }

    private void aggiornaVisualizzazioneIngredienti(Ingrediente ingrediente, EditText quantitaIngrediente, TextView unitaMisura){
        quantitaIngrediente.setText(ingrediente.getQuantitaAssolutaToString());
        if( ingrediente.getTipo().getNome().equalsIgnoreCase("acqua") ) {
            unitaMisura.setText(" L");
        }else{
            unitaMisura.setText(" Kg");
        }


    }

}