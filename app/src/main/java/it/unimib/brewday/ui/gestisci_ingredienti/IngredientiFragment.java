package it.unimib.brewday.ui.gestisci_ingredienti;

import static android.widget.Toast.LENGTH_SHORT;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.Ingrediente;


public class IngredientiFragment extends Fragment {

    ListView listViewIngredientiDispobili;

    IngredienteViewModel ingredienteViewModel;

    AdapterListViewListaIngredientiDisponibili adapterListViewListaIngredientiDisponibili;

    List<Ingrediente> listaIngredienti;

    int posizionePrecedente = -1;
    EditText quantitaIngredientePrecedente;


    public IngredientiFragment() {
        // Required empty public constructor
    }

    public static IngredientiFragment newInstance() {

        return new IngredientiFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredienteViewModel = new ViewModelProvider(this,
                new IngredienteViewModelFactory(getContext()))
                .get(IngredienteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getViewLifecycleOwner();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredienti, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewIngredientiDispobili = view.findViewById(R.id.listView_ingredrientiDisponibili);

        ingredienteViewModel.readAllIngredienti();
        ingredienteViewModel.readAllIngredientiMutableLiveData.observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()) {
                listaIngredienti = ((Risultato.IngredientiSuccesso) risultato).getData();

                adapterListViewListaIngredientiDisponibili = new AdapterListViewListaIngredientiDisponibili(getContext(), 0, listaIngredienti, R.layout.lista_ingredienti_singoli, new AdapterListViewListaIngredientiDisponibili.OnItemClickListener() {
                    @Override
                    public void onAddIngredienteClick(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {

                        aggiungiQuantitaIngrediente(ingrediente, position, quantitaIngrediente);
                        aggiornaDBIngrediente(ingrediente);
                    }

                    @Override
                    public void onRemoveIngredienteClick(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {
                       
                        if (ingrediente.getQuantitaPosseduta() < 1) {
                            Snackbar.make(view, "Non si possono avere ingredienti negativi", LENGTH_SHORT).show();
                        }else {
                            togliQuantitaIngrediente(ingrediente, position, quantitaIngrediente);
                            aggiornaDBIngrediente(ingrediente);                        }

                    }
                }, (ingrediente, quantitaIngrediente, position) -> {
                    inizializzaPositionePrecedente(position, quantitaIngrediente);
                    controlloCambioSelezione(position, quantitaIngrediente);
                    quantitaIngrediente.setOnKeyListener((v, keyCode, event) -> {

                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                            ingrediente.setQuantitaPosseduta(verificaIngrediente(quantitaIngrediente));
                            aggiornaDBIngrediente(ingrediente);
                        }
                        return false;
                    });
                });

                listViewIngredientiDispobili.setAdapter(adapterListViewListaIngredientiDisponibili);
                listViewIngredientiDispobili.setDivider(null);
                
            } else {
                Snackbar.make(view, ((Risultato.Errore) risultato).getMessage(), LENGTH_SHORT).show();
            }
        });
    }

    public int verificaIngrediente(EditText quantitaIngrediente) {

        if (quantitaIngrediente.getText().length() == 0) {

            quantitaIngrediente.setText("0");
            return 0;

        }else {

            quantitaIngrediente.setText(String.valueOf(Integer.parseInt(quantitaIngrediente.getText().toString())));
            return Integer.parseInt(quantitaIngrediente.getText().toString());
        }
    }

    public int quantitaBottone(int position) {
        
        if (position == 0) {
            return 1;
        } else {
            return 10;
        }


    }
    public void inizializzaPositionePrecedente(int position, EditText quantitaIngrediente) {

        if (posizionePrecedente == -1) {
            posizionePrecedente = position;
            quantitaIngredientePrecedente = quantitaIngrediente;
        }

    }
    public void controlloCambioSelezione(int position, EditText quantitaIngrediente) {

        if (posizionePrecedente != position) {
            verificaIngrediente(quantitaIngredientePrecedente);
            posizionePrecedente = position;
            quantitaIngredientePrecedente = quantitaIngrediente;
        }

    }


    public void togliQuantitaIngrediente(Ingrediente ingrediente, int position, EditText quantitaIngrediente){
        if (ingrediente.getQuantitaPosseduta() < 10 && quantitaBottone(position) == 10) {
            ingrediente.setQuantitaPosseduta(0);
        } else {
            ingrediente.setQuantitaPosseduta(verificaIngrediente(quantitaIngrediente) - quantitaBottone(position));
        }

    }

    public void aggiungiQuantitaIngrediente(Ingrediente ingrediente, int position, EditText quantitaIngrediente){
            ingrediente.setQuantitaPosseduta(verificaIngrediente(quantitaIngrediente) + quantitaBottone(position));
    }

    public void aggiornaDBIngrediente(Ingrediente ingrediente){
        ingredienteViewModel.updateIngrediente(ingrediente);


    }

}