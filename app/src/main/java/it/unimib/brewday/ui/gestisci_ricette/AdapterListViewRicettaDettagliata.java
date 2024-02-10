package it.unimib.brewday.ui.gestisci_ricette;

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
import it.unimib.brewday.model.IngredienteRicetta;


public class AdapterListViewRicettaDettagliata extends ArrayAdapter<IngredienteRicetta> {

    private final List<IngredienteRicetta> listaIngredienti;
    private final boolean visibile;
    private final OnItemClickListenerA onItemClickListener;
    private final int layout;
    private final OnFocusChangeListenerA onFocusChangeListener;

    private int posizionePrecedente = -1;
    private EditText quantitaIngredientePrecedente;
    private IngredienteRicetta ingredientePrecedente;


    public AdapterListViewRicettaDettagliata(@NonNull Context context, int resource, List<IngredienteRicetta> listaIngredienti,
                                             int layout, OnItemClickListenerA onItemClickListener, OnFocusChangeListenerA onFocusChangeListenerA, boolean visibile) {
        super(context, resource, listaIngredienti);
        this.listaIngredienti = listaIngredienti;
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
        this.onFocusChangeListener = onFocusChangeListenerA;
        this.visibile = visibile;
    }


    public interface OnItemClickListenerA {

        void onAddIngredienteClick(IngredienteRicetta ingredienteRicetta);

        void onRemoveIngredienteClick(IngredienteRicetta ingredienteRicetta);

    }

    public interface OnFocusChangeListenerA {

        void onChangeIngrediente(IngredienteRicetta ingredienteRicetta);
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

        nomeIngrediente.setText(listaIngredienti.get(position).getTipoIngrediente().getNome());
        aggiornaVisualizzazioneIngredienti(listaIngredienti.get(position), quantitaIngrediente, unitaMisura);

        IngredienteRicetta ingredienteAdapter = verificaIngrediente(listaIngredienti.get(position), quantitaIngrediente);


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
            if (ingredienteAdapter.getDosaggioIngrediente() < 1) {
                Snackbar.make(finalConvertView, R.string.no_iingredienti_negativi, LENGTH_SHORT).show();
            } else {
                togliQuantitaIngrediente(verificaIngrediente(ingredienteAdapter, quantitaIngrediente), position, quantitaIngrediente);
                onItemClickListener.onRemoveIngredienteClick(ingredienteAdapter);
            }

            aggiornaVisualizzazioneIngredienti(ingredienteAdapter, quantitaIngrediente, unitaMisura);
        });


        quantitaIngrediente.setOnFocusChangeListener((v, hasFocus) ->
        {
            inizializzaPositionePrecedente(ingredienteAdapter, position, quantitaIngrediente);
            controlloCambioSelezione(ingredienteAdapter, position, quantitaIngrediente);
            rispostaInvioTastiera(ingredienteAdapter, quantitaIngrediente);


        });

        return convertView;
    }

    private void aggiornaVisualizzazioneIngredienti(IngredienteRicetta ingredienteRicetta, EditText quantitaIngrediente, TextView unitaMisura) {
        quantitaIngrediente.setText(ingredienteRicetta.getDosaggioIngredienteToString());
        if (ingredienteRicetta.getTipoIngrediente().getNome().equalsIgnoreCase(ACQUA)) {
            unitaMisura.setText(" L");
        } else {
            unitaMisura.setText(" g");
        }


    }

    private void aggiungiQuantitaIngrediente(IngredienteRicetta ingredienteRicetta, int position, EditText quantitaIngrediente) {
        ingredienteRicetta.setDosaggioIngrediente(verificaIngrediente(ingredienteRicetta, quantitaIngrediente).getDosaggioIngrediente() + quantitaBottone(position));
        quantitaIngrediente.setText(ingredienteRicetta.getDosaggioIngredienteToString());

    }

    private IngredienteRicetta verificaIngrediente(IngredienteRicetta ingredienteRicetta, EditText quantitaIngrediente) {

        if (quantitaIngrediente.getText().length() == 0) {
            ingredienteRicetta.setDosaggioIngrediente(0);
            quantitaIngrediente.setText("0");
        } else {
            quantitaIngrediente.setText(String.valueOf(Integer.parseInt(quantitaIngrediente.getText().toString())));
            ingredienteRicetta.setDosaggioIngrediente(Integer.parseInt(quantitaIngrediente.getText().toString()));
        }

        return ingredienteRicetta;
    }

    private int quantitaBottone(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 10;
        }

    }

    private void togliQuantitaIngrediente(IngredienteRicetta ingredienteRicetta, int position, EditText quantitaIngrediente) {

        if (ingredienteRicetta.getDosaggioIngrediente() < 10 && quantitaBottone(position) == 10) {
            ingredienteRicetta.setDosaggioIngrediente(0);
        } else {
            ingredienteRicetta.setDosaggioIngrediente(verificaIngrediente(ingredienteRicetta, quantitaIngrediente).getDosaggioIngrediente() - quantitaBottone(position));
        }
        quantitaIngrediente.setText(ingredienteRicetta.getDosaggioIngredienteToString());

    }


    private void inizializzaPositionePrecedente(IngredienteRicetta ingredienteRicetta, int position, EditText quantitaIngrediente) {

        if (posizionePrecedente == -1) {
            posizionePrecedente = position;
            quantitaIngredientePrecedente = quantitaIngrediente;
            ingredientePrecedente = ingredienteRicetta;
        }

    }

    private void controlloCambioSelezione(IngredienteRicetta ingredienteRicetta, int position, EditText quantitaIngrediente) {

        if (posizionePrecedente != position) {
            salvaQuantita( ingredientePrecedente, quantitaIngredientePrecedente);
            posizionePrecedente = position;
            quantitaIngredientePrecedente = quantitaIngrediente;
            ingredientePrecedente = ingredienteRicetta;


        }


    }

    private void rispostaInvioTastiera(IngredienteRicetta ingredienteRicetta, EditText quantitaIngrediente) {
        quantitaIngrediente.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                salvaQuantita(ingredienteRicetta,  quantitaIngrediente);
            }
            return false;
        });
    }

    private void salvaQuantita(IngredienteRicetta ingredienteRicetta, EditText quantitaIngrediente ){
        verificaIngrediente(ingredienteRicetta, quantitaIngrediente);
        onFocusChangeListener.onChangeIngrediente(ingredienteRicetta);

    }
}
