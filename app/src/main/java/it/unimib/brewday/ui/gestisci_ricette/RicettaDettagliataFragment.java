package it.unimib.brewday.ui.gestisci_ricette;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentRicettaDettagliataBinding;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.ui.gestisci_ingredienti.AdapterListViewListaIngredientiDisponibili;
import it.unimib.brewday.util.ListaIngredienti;


public class RicettaDettagliataFragment extends Fragment {


    AdapterListViewListaIngredientiDisponibili adapterListViewListaIngredientiRicetta;
    private FragmentRicettaDettagliataBinding fragmentRicettaDettagliataBinding;
    boolean visibile;

    int posizionePrecedente = -1;
    EditText quantitaIngredientePrecedente;

    Ingrediente ingredientePrecedente;
    public RicettaDettagliataFragment() {
        // Required empty public constructor
    }


    public static RicettaDettagliataFragment newInstance() {
        return new RicettaDettagliataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRicettaDettagliataBinding = FragmentRicettaDettagliataBinding.inflate(inflater, container, false);
        return fragmentRicettaDettagliataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ListView listViewIngredientiRicettaDettagliata = fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata;
        Button modificaRicettaButton = fragmentRicettaDettagliataBinding.buttonModificaRicetta;
        EditText numeroLitriRicettaBirra = fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra;
        EditText nomeRicetta = fragmentRicettaDettagliataBinding.textViewNomeRicetta;


        ListaIngredienti listaIngredienti = new ListaIngredienti();
        List<Ingrediente> listaIngredientiRicetta = listaIngredienti.getListaIngredienti();

        visibile = false;

        setAdapterIngredienti(visibile, view, listaIngredientiRicetta);
        numeroLitriRicettaBirra.setEnabled(visibile);
        nomeRicetta.setEnabled(visibile);

        modificaRicettaButton.setOnClickListener(v ->
                setVisibile(visibile, view, numeroLitriRicettaBirra, listaIngredientiRicetta)
        );

        numeroLitriRicettaBirra.setOnFocusChangeListener((v, hasFocus) ->
                verificaNumeroLitriBirra(numeroLitriRicettaBirra, hasFocus)
        );


}

    public void setVisibile(boolean invertiVisibile, View view, EditText numeroLitriBirra, List<Ingrediente> listaIngredientiRicetta){


        if (!invertiVisibile){
            fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText(R.string.conferma);
            visibile = !invertiVisibile;
        } else {
            if( controlloCreazione(view, fragmentRicettaDettagliataBinding.textViewNomeRicetta, fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra)) {
                int zeroIngredinti = 0;
                double litriScelti = Double.parseDouble(numeroLitriBirra.getText().toString());
                List<Double> listaIngredientiPerLitro = new ArrayList<>();

                for (Ingrediente ingrediente : listaIngredientiRicetta) {
                    if (ingrediente.getQuantitaPosseduta() == 0) {
                        zeroIngredinti++;
                    }
                    listaIngredientiPerLitro.add((ingrediente.getQuantitaPosseduta() / litriScelti));
                }
                salvaRicetta(view, zeroIngredinti, listaIngredientiRicetta, listaIngredientiPerLitro);

                fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText(R.string.modifica);
                visibile = !invertiVisibile;
            }
        }

        fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra.setEnabled(visibile);
        fragmentRicettaDettagliataBinding.textViewNomeRicetta.setEnabled(visibile);
        setAdapterIngredienti(visibile, view, listaIngredientiRicetta);

    }

    public void setAdapterIngredienti(boolean visible, View view, List<Ingrediente> listaIngredientiRicetta){
        adapterListViewListaIngredientiRicetta = new AdapterListViewListaIngredientiDisponibili(
                getContext(), 0, listaIngredientiRicetta, R.layout.lista_ingredienti_singoli,
                new AdapterListViewListaIngredientiDisponibili.OnItemClickListener() {
                    @Override
                    public void onAddIngredienteClick(Ingrediente ingrediente) {
                        //vuoto
                    }

                    @Override
                    public void onRemoveIngredienteClick(Ingrediente ingrediente) {
                        //vuoto
                    }
                }, (ingrediente) -> {
           //vuoto
        }, visible);

        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setAdapter(adapterListViewListaIngredientiRicetta);
        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setDivider(null);
    }

    public Ingrediente verificaIngrediente(Ingrediente ingrediente, EditText quantitaIngrediente) {

        if (quantitaIngrediente.getText().length() == 0) {
            ingrediente.setQuantitaPosseduta(0);
            quantitaIngrediente.setText("0");
        } else {
            quantitaIngrediente.setText(String.valueOf(Integer.parseInt(quantitaIngrediente.getText().toString())));
            ingrediente.setQuantitaPosseduta(Integer.parseInt(quantitaIngrediente.getText().toString()));
        }

        return ingrediente;
    }

    public int quantitaBottone(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 10;
        }


    }
    public void inizializzaPositionePrecedente(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {

        if (posizionePrecedente == -1) {
            posizionePrecedente = position;
            quantitaIngredientePrecedente = quantitaIngrediente;
            ingredientePrecedente = ingrediente;
        }

    }
    public void controlloCambioSelezione(Ingrediente ingrediente, int position, EditText quantitaIngrediente, List<Ingrediente> listaIngredientiRicetta) {

        if (posizionePrecedente != position) {
            aggiornaListaIngrediente(verificaIngrediente(ingredientePrecedente, quantitaIngredientePrecedente), posizionePrecedente, listaIngredientiRicetta);
            posizionePrecedente = position;
            quantitaIngredientePrecedente = quantitaIngrediente;
            ingredientePrecedente = ingrediente;
        }

    }


    public void togliQuantitaIngrediente(Ingrediente ingrediente, int position, EditText quantitaIngrediente){
        if (ingrediente.getQuantitaPosseduta() < 10 && quantitaBottone(position) == 10) {
            ingrediente.setQuantitaPosseduta(0);
        } else {
            ingrediente.setQuantitaPosseduta(verificaIngrediente(ingrediente ,quantitaIngrediente).getQuantitaPosseduta() - quantitaBottone(position));
        }
        quantitaIngrediente.setText(ingrediente.getQuantitaAssolutaToString());

    }

    public void aggiungiQuantitaIngrediente(Ingrediente ingrediente, int position, EditText quantitaIngrediente){
        ingrediente.setQuantitaPosseduta(verificaIngrediente(ingrediente, quantitaIngrediente).getQuantitaPosseduta() + quantitaBottone(position));
        quantitaIngrediente.setText(ingrediente.getQuantitaAssolutaToString());
    }

    public void aggiornaListaIngrediente(Ingrediente ingrediente, int position, List<Ingrediente> listaIngredientiRicetta){
        listaIngredientiRicetta.get(position).setQuantitaPosseduta(ingrediente.getQuantitaPosseduta());
    }

    public void resetQuantitaLasciatoTestoVuoto(Ingrediente ingrediente, int position, EditText quantitaIngrediente, List<Ingrediente> listaIngredientiRicetta) {
        if (quantitaIngrediente.getText().length() == 0) {
            ingrediente.setQuantitaPosseduta(0);
            aggiornaListaIngrediente(ingrediente, position, listaIngredientiRicetta);
        }
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
            Snackbar.make(view, "Nome ricetta mancante", LENGTH_SHORT).show();
            return false;
        } else if( Double.parseDouble(numeroLitriBirra.getText().toString()) == 0.0 ){
            Snackbar.make(view, "Litri di birra mancanti", LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void salvaRicetta(View view, int zeroIngredinti , List<Ingrediente> listaIngredientiRicetta, List<Double> listaIngredientiPerLitro) {
        if (zeroIngredinti < 3) {
            //TODO chiamata luca con listaIngredientiPerLitro
            setAdapterIngredienti(false, view, listaIngredientiRicetta);

        } else {
            Snackbar.make(view, "scegli più ingredienti", LENGTH_SHORT).show();
        }
    }

    public void rispostaInvioTastiera(Ingrediente ingrediente, int position, EditText quantitaIngrediente, List<Ingrediente> listaIngredientiRicetta){
        quantitaIngrediente.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                aggiornaListaIngrediente(verificaIngrediente(ingrediente, quantitaIngrediente), position, listaIngredientiRicetta);
            }
            return false;
        });
    }
}
