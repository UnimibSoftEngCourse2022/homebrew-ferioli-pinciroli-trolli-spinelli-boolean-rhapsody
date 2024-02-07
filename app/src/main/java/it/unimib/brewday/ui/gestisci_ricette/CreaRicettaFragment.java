package it.unimib.brewday.ui.gestisci_ricette;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.ui.gestisci_ingredienti.AdapterListViewListaIngredientiDisponibili;

public class CreaRicettaFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_crea_ricetta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listViewIngredientiRicetta = view.findViewById(R.id.listView_ingredrientiRicetta);
        Button creaRicettaButton = view.findViewById(R.id.button_creaRicetta);

        List<Ingrediente> listIngredienti = new ArrayList<>();
        listIngredienti.add(new Ingrediente(1, TipoIngrediente.ACQUA, 0.0));
        listIngredienti.add(new Ingrediente(2, TipoIngrediente.MALTO, 0.0));
        listIngredienti.add(new Ingrediente(3, TipoIngrediente.LUPPOLO, 0.0));
        listIngredienti.add(new Ingrediente(4, TipoIngrediente.LIEVITI, 0.0));
        listIngredienti.add(new Ingrediente(5, TipoIngrediente.ZUCCHERO, 0.0));
        listIngredienti.add(new Ingrediente(6, TipoIngrediente.ADDITIVI, 0.0));

        AdapterListViewListaIngredientiDisponibili adapterListViewListaIngredientiRicetta = new AdapterListViewListaIngredientiDisponibili(
                getContext(), 0, listIngredienti, R.layout.lista_ingredienti_singoli,
                new AdapterListViewListaIngredientiDisponibili.OnItemClickListener() {
                    @Override
                    public void onAddIngredienteClick(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {

                    }

                    @Override
                    public void onRemoveIngredienteClick(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {

                    }
                }, (ingrediente, quantitaIngrediente, position) -> {});

        listViewIngredientiRicetta.setAdapter(adapterListViewListaIngredientiRicetta);
        listViewIngredientiRicetta.setDivider(null);

        creaRicettaButton.setOnClickListener(v -> {

        });
    }
}