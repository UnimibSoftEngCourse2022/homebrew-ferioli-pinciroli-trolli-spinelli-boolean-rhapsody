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
        ingredienteViewModel.getReadAllIngredientiResult().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                listaIngredienti = ((Risultato.IngredientiSuccesso) risultato).getData();

                adapterListViewListaIngredientiDisponibili = new AdapterListViewListaIngredientiDisponibili(getContext(), 0, listaIngredienti, R.layout.lista_ingredienti_singoli, new AdapterListViewListaIngredientiDisponibili.OnItemClickListener() {
                    @Override
                    public void onAddIngredienteClick(Ingrediente ingrediente) {
                        aggiornaDBIngrediente(ingrediente);
                    }

                    @Override
                    public void onRemoveIngredienteClick(Ingrediente ingrediente) {
                        aggiornaDBIngrediente(ingrediente);
                    }
                }, ingrediente ->

                    aggiornaDBIngrediente( ingrediente)
                , true);

                listViewIngredientiDispobili.setAdapter(adapterListViewListaIngredientiDisponibili);
                listViewIngredientiDispobili.setDivider(null);

            } else {
                Snackbar.make(view, ((Risultato.Errore) risultato).getMessaggio(), LENGTH_SHORT).show();
            }
        });
    }

    private void aggiornaDBIngrediente(Ingrediente ingrediente){
        ingredienteViewModel.updateIngrediente(ingrediente);


    }



}