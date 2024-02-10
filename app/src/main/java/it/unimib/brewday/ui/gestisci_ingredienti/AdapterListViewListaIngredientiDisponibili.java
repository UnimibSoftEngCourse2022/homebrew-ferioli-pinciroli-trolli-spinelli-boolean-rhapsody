package it.unimib.brewday.ui.gestisci_ingredienti;

import static android.widget.Toast.LENGTH_SHORT;

import static it.unimib.brewday.util.Constants.ACQUA;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ingrediente;

public class AdapterListViewListaIngredientiDisponibili extends ArrayAdapter<Ingrediente> {


    private final List<Ingrediente> listaIngredienti;
    private final boolean visibile;

    private final int layout;

    private final OnItemClickListener onItemClickListener;

    private final OnFocusChangeListener onFocusChangeListener;

    private int posizionePrecedente = -1;
    private EditText quantitaIngredientePrecedente;

    private Ingrediente ingredientePrecedente;


    public AdapterListViewListaIngredientiDisponibili(@NonNull Context context, int resource, List<Ingrediente> listaIngredienti,
                                                      int layout, OnItemClickListener onItemClickListener, OnFocusChangeListener onFocusChangeListener, boolean visibile) {
        super(context, resource, listaIngredienti);
        this.listaIngredienti = listaIngredienti;
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
        this.onFocusChangeListener = onFocusChangeListener;
        this.visibile = visibile;
    }

    public interface OnItemClickListener {

        void onAddIngredienteClick(Ingrediente ingrediente);

        void onRemoveIngredienteClick(Ingrediente ingrediente);

    }

    public interface OnFocusChangeListener {

        void onChangeIngrediente(Ingrediente ingrediente);
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

        nomeIngrediente.setText(listaIngredienti.get(position).getTipo().getNome());
        aggiornaVisualizzazioneIngredienti(listaIngredienti.get(position), quantitaIngrediente, unitaMisura);

        Ingrediente ingredienteAdapter = verificaIngrediente(listaIngredienti.get(position), quantitaIngrediente);
        ingredienteAdapter.getQuantitaPosseduta();

        if (!visibile) {
            aggiungiIngrediente.setVisibility(View.GONE);
            rimuoviIngrediente.setVisibility(View.GONE);
            quantitaIngrediente.setFocusable(false);
        }

        aggiungiIngrediente.setOnClickListener(v -> {
            aggiungiQuantitaIngrediente(ingredienteAdapter, position, quantitaIngrediente);
            onItemClickListener.onAddIngredienteClick(ingredienteAdapter);
            aggiornaVisualizzazioneIngredienti(ingredienteAdapter, quantitaIngrediente, unitaMisura);
        });

        View finalConvertView = convertView;
        rimuoviIngrediente.setOnClickListener(v -> {
            if (listaIngredienti.get(position).getQuantitaPosseduta() < 1) {
                Snackbar.make(finalConvertView, R.string.no_iingredienti_negativi, LENGTH_SHORT).show();
            } else {
                togliQuantitaIngrediente(verificaIngrediente(ingredienteAdapter, quantitaIngrediente), position, quantitaIngrediente);
                onItemClickListener.onRemoveIngredienteClick(ingredienteAdapter);
            }

            aggiornaVisualizzazioneIngredienti(ingredienteAdapter, quantitaIngrediente, unitaMisura);
        });


        quantitaIngrediente.setOnFocusChangeListener((v, hasFocus) ->
        {
            resetQuantitaLasciatoTestoVuoto(ingredienteAdapter, quantitaIngrediente);
            inizializzaPositionePrecedente(ingredienteAdapter, position, quantitaIngrediente);
            controlloCambioSelezione(ingredienteAdapter, position, quantitaIngrediente);
            if (rispostaInvioTastiera(quantitaIngrediente)) {
                onFocusChangeListener.onChangeIngrediente(ingredienteAdapter);
            }

        });

        return convertView;
    }

    private void aggiornaVisualizzazioneIngredienti(Ingrediente ingrediente, EditText quantitaIngrediente, TextView unitaMisura) {
        quantitaIngrediente.setText(ingrediente.getQuantitaAssolutaToString());
        if (ingrediente.getTipo().getNome().equalsIgnoreCase(ACQUA)) {
            unitaMisura.setText(" L");
        } else {
            unitaMisura.setText(" g");
        }


    }

    private void aggiungiQuantitaIngrediente(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {
        ingrediente.setQuantitaPosseduta(verificaIngrediente(ingrediente, quantitaIngrediente).getQuantitaPosseduta() + quantitaBottone(position));
        quantitaIngrediente.setText(ingrediente.getQuantitaAssolutaToString());

    }

    private Ingrediente verificaIngrediente(Ingrediente ingrediente, EditText quantitaIngrediente) {

        if (quantitaIngrediente.getText().length() == 0) {
            ingrediente.setQuantitaPosseduta(0);
            quantitaIngrediente.setText("0");
        } else {
            quantitaIngrediente.setText(String.valueOf(Integer.parseInt(quantitaIngrediente.getText().toString())));
            ingrediente.setQuantitaPosseduta(Integer.parseInt(quantitaIngrediente.getText().toString()));
        }

        return ingrediente;
    }

    private int quantitaBottone(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 10;
        }

    }

    private void togliQuantitaIngrediente(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {
        if (ingrediente.getQuantitaPosseduta() < 10 && quantitaBottone(position) == 10) {
            ingrediente.setQuantitaPosseduta(0);
        } else {
            ingrediente.setQuantitaPosseduta(verificaIngrediente(ingrediente, quantitaIngrediente).getQuantitaPosseduta() - quantitaBottone(position));
        }
        quantitaIngrediente.setText(ingrediente.getQuantitaAssolutaToString());

    }

    private void resetQuantitaLasciatoTestoVuoto(Ingrediente ingrediente, EditText quantitaIngrediente) {
        if (quantitaIngrediente.getText().length() == 0) {
            ingrediente.setQuantitaPosseduta(0);
        }
    }

    private void inizializzaPositionePrecedente(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {

        if (posizionePrecedente == -1) {
            posizionePrecedente = position;
            quantitaIngredientePrecedente = quantitaIngrediente;
            ingredientePrecedente = ingrediente;
        }

    }

    private void controlloCambioSelezione(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {

        if (posizionePrecedente != position) {
            posizionePrecedente = position;
            quantitaIngredientePrecedente = quantitaIngrediente;
            ingredientePrecedente = ingrediente;
        }

    }

    private boolean rispostaInvioTastiera(EditText quantitaIngrediente) {
        quantitaIngrediente.setOnKeyListener((v, keyCode, event) ->
                (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER));
        return false;
    }
}