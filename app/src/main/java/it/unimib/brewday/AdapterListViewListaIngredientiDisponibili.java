package it.unimib.brewday;

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

import it.unimib.brewday.model.Ingrediente;

public class AdapterListViewListaIngredientiDisponibili extends ArrayAdapter<Ingrediente> {


    private final List<Ingrediente> listaIngredienti;


    private final int layout;

    private final OnItemClickListener onItemClickListener;

    public AdapterListViewListaIngredientiDisponibili(@NonNull Context context, int resource, List<Ingrediente> listaIngredienti, int layout, OnItemClickListener onItemClickListener) {
        super(context, resource, listaIngredienti);
        this.listaIngredienti = listaIngredienti;
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onAddIngredienteClick(Ingrediente ingrediente, int position);

        void onRemoveIngredienteClick(Ingrediente ingrediente, int position);

    }


    @Override
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }
        TextView nomeIngrediente = convertView.findViewById(R.id.textView_ingrediente);
        EditText quantitàIngrediente = convertView.findViewById(R.id.editTextText_ingrediente);
        FloatingActionButton aggiungiIngrediente = convertView.findViewById(R.id.button_aggiungi_ingrediente);
        FloatingActionButton rimuoviIngrediente = convertView.findViewById(R.id.button_rimuovi_ingrediente);

        aggiungiIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onAddIngredienteClick(listaIngredienti.get(position), position);
                aggiornaVisualizzazioneIngredienti(listaIngredienti, position, quantitàIngrediente);
            }
        });
        rimuoviIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onRemoveIngredienteClick(listaIngredienti.get(position), position);
                aggiornaVisualizzazioneIngredienti(listaIngredienti, position, quantitàIngrediente);
            }
        });


        nomeIngrediente.setText(listaIngredienti.get(position).getNome());
        aggiornaVisualizzazioneIngredienti(listaIngredienti, position, quantitàIngrediente);
        return convertView;
    }

    private void aggiornaVisualizzazioneIngredienti(List<Ingrediente> listaIngredienti, int position, EditText quantitàIngrediente){
        if( listaIngredienti.get(position).getNome().equalsIgnoreCase("acqua") ) {
            quantitàIngrediente.setText(listaIngredienti.get(position).getQuantitàAssolutaToString() + " L");
        }else{
            quantitàIngrediente.setText(listaIngredienti.get(position).getQuantitàAssolutaToString() + " Kg");
        }


    }

}