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
import android.widget.ListView;


import com.google.android.material.snackbar.Snackbar;

import it.unimib.brewday.databinding.FragmentCreaRicettaBinding;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.ui.gestisci_ingredienti.AdapterListViewListaIngredientiDisponibili;
import it.unimib.brewday.util.ListaIngredienti;

public class CreaRicettaFragment extends Fragment {

    private  FragmentCreaRicettaBinding fragmentCreaRicettaBinding;
    int posizionePrecedente = -1;
    EditText quantitaIngredientePrecedente;

    Ingrediente ingredientePrecedente;
    List<Ingrediente> listaIngredientiRicetta;



    public CreaRicettaFragment() {
        // Required empty public constructor
    }

    public static CreaRicettaFragment newInstance() {

        return new CreaRicettaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCreaRicettaBinding = FragmentCreaRicettaBinding.inflate(inflater, container, false);
        return fragmentCreaRicettaBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listViewIngredientiRicetta = fragmentCreaRicettaBinding.listViewIngredrientiRicetta;
        Button creaRicettaButton = fragmentCreaRicettaBinding.buttonCreaRicetta;
        EditText numeroLitriBirra = fragmentCreaRicettaBinding.editNumberNumeroLitriBirra;
        EditText nomeRicetta = fragmentCreaRicettaBinding.editTextNomeRicetta;


        ListaIngredienti listaIngredienti = new ListaIngredienti();
        listaIngredientiRicetta = listaIngredienti.getListaIngredienti();

        AdapterListViewListaIngredientiDisponibili adapterListViewListaIngredientiRicetta = new AdapterListViewListaIngredientiDisponibili(
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

        }, true);

        listViewIngredientiRicetta.setAdapter(adapterListViewListaIngredientiRicetta);
        listViewIngredientiRicetta.setDivider(null);

        numeroLitriBirra.setOnFocusChangeListener((v, hasFocus) ->
               verificaNumeroLitriBirra(numeroLitriBirra, hasFocus)
        );

        creaRicettaButton.setOnClickListener(v -> {

                    if( controlloCreazione(view, nomeRicetta, numeroLitriBirra)){
                    int zeroIngredinti = 0;
                    double litriScelti = Double.parseDouble(numeroLitriBirra.getText().toString());
                    List<Double> listaIngredientiPerLitro = new ArrayList<>();

                    for (Ingrediente ingrediente: listaIngredientiRicetta) {
                            if(ingrediente.getQuantitaPosseduta() == 0) {
                               zeroIngredinti ++;
                            }
                            listaIngredientiPerLitro.add((ingrediente.getQuantitaPosseduta() / litriScelti));
                    }
                    salvaRicetta(view, zeroIngredinti);
                }
        });
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
    public void controlloCambioSelezione(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {

        if (posizionePrecedente != position) {
            aggiornaListaIngrediente(verificaIngrediente(ingredientePrecedente, quantitaIngredientePrecedente), posizionePrecedente);
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

    public void aggiornaListaIngrediente(Ingrediente ingrediente, int position){
        listaIngredientiRicetta.get(position).setQuantitaPosseduta(ingrediente.getQuantitaPosseduta());
    }

    public void resetQuantitaLasciatoTestoVuoto(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {
        if (quantitaIngrediente.getText().length() == 0) {
            ingrediente.setQuantitaPosseduta(0);
            aggiornaListaIngrediente(ingrediente, position);
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
            Snackbar.make(view, R.string.nome_ricetta_mancante, LENGTH_SHORT).show();
            return false;
        } else if( Double.parseDouble(numeroLitriBirra.getText().toString()) == 0.0 ){
            Snackbar.make(view, R.string.litri_birra_ricetta_mancante, LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void salvaRicetta(View view, int zeroIngredinti ) {
        if (zeroIngredinti < 3) {
            //TODO chiamata luca
        } else {
            Snackbar.make(view, R.string.ingredienti_ricetta_mancanti, LENGTH_SHORT).show();
        }
    }

    public void rispostaInvioTastiera(Ingrediente ingrediente, int position, EditText quantitaIngrediente){
        quantitaIngrediente.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                aggiornaListaIngrediente(verificaIngrediente(ingrediente, quantitaIngrediente), position);
            }
            return false;
        });
    }
}