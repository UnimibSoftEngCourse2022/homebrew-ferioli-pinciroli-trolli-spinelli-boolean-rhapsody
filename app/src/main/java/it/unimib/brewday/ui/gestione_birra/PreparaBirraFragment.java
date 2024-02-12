package it.unimib.brewday.ui.gestione_birra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentPreparaBirraBinding;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.TipoIngrediente;


public class PreparaBirraFragment extends Fragment {

 private FragmentPreparaBirraBinding fragmentPreparaBirraBinding;

 private AdapterListViewIngredientiBirra adapterListViewIngredientiBirra;
    public PreparaBirraFragment() {
        // Required empty public constructor
    }


    public static PreparaBirraFragment newInstance() {

        return new PreparaBirraFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPreparaBirraBinding = FragmentPreparaBirraBinding.inflate(inflater, container,false);
        return fragmentPreparaBirraBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<IngredienteRicetta> listaIngredientiBirra = new ArrayList<>();

        listaIngredientiBirra.add(new IngredienteRicetta(TipoIngrediente.ACQUA, 0.0));
        listaIngredientiBirra.add(new IngredienteRicetta(TipoIngrediente.ACQUA, 0.0));
        listaIngredientiBirra.add(new IngredienteRicetta(TipoIngrediente.ACQUA, 0.0));
        listaIngredientiBirra.add(new IngredienteRicetta(TipoIngrediente.ACQUA, 0.0));
        listaIngredientiBirra.add(new IngredienteRicetta(TipoIngrediente.ACQUA, 0.0));
        listaIngredientiBirra.add(new IngredienteRicetta(TipoIngrediente.ACQUA, 0.0));

        adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(getContext(), R.layout.lista_ingredienti_birra, listaIngredientiBirra);

    }
}