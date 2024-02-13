package it.unimib.brewday.ui.gestione_birra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentPreparaBirraBinding;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.ui.gestisci_ingredienti.IngredienteViewModel;
import it.unimib.brewday.ui.gestisci_ingredienti.IngredienteViewModelFactory;
import it.unimib.brewday.ui.gestisci_ricette.RicetteViewModel;
import it.unimib.brewday.ui.gestisci_ricette.RicetteViewModelFactory;


public class PreparaBirraFragment extends Fragment {

    private FragmentPreparaBirraBinding fragmentPreparaBirraBinding;
    private RicetteViewModel ricetteViewModel;
    private BirraViewModel birraViewModel;
    private IngredienteViewModel ingredienteViewModel;

    List<IngredienteRicetta> listaIngredientiBirra = new ArrayList<>();
    List<Ingrediente> listaIngredientiDisponibili = new ArrayList<>();
    List<Attrezzo> listaAttrezziDisponibili = new ArrayList<>();
    List<Ingrediente> listaIngredientiDifferenza;

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
        ricetteViewModel = new ViewModelProvider(this,
                new RicetteViewModelFactory(getContext())).get(RicetteViewModel.class);
        birraViewModel = new ViewModelProvider(this,
                new BirraViewModelFactory(getContext())).get(BirraViewModel.class);
        ingredienteViewModel = new ViewModelProvider(this,
                new IngredienteViewModelFactory(getContext())).get(IngredienteViewModel.class);
        listaIngredientiDisponibili = new ArrayList<>();
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

        Ricetta ricetta = PreparaBirraFragmentArgs.fromBundle(getArguments()).getRicetta();
        int litriBirraScelti = PreparaBirraFragmentArgs.fromBundle(getArguments()).getNumeroLitriBirraScelti();


        ricetteViewModel.getIngredientiRicetta(ricetta.getId());
        fragmentPreparaBirraBinding.textViewNomePreparaBirra.setText(ricetta.getNome());


        ricetteViewModel.getIngredientiRicetteRisultato().observe(getViewLifecycleOwner(), risultato ->  {
            if (risultato.isSuccessful()){
                listaIngredientiBirra = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();
                ingredienteViewModel.readAllIngredienti();

                for (IngredienteRicetta ingredienteRicetta : listaIngredientiBirra) {
                    if (ingredienteRicetta.getTipoIngrediente().equals(TipoIngrediente.ACQUA)){
                        ingredienteRicetta.setDosaggioIngrediente(round(ingredienteRicetta.getDosaggioIngrediente()*litriBirraScelti, 1));
                    } else {
                        ingredienteRicetta.setDosaggioIngrediente(Math.round(ingredienteRicetta.getDosaggioIngrediente()*litriBirraScelti));
                    }
                }


            } else {
                Snackbar.make(view, "non riesco a recuperare gli ingredienti", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        ingredienteViewModel.getReadAllIngredientiResult().observe(getViewLifecycleOwner(),risultato -> {
            if (risultato.isSuccessful()){
                listaIngredientiDisponibili = ((Risultato.IngredientiSuccesso) risultato).getData();

                calcolaDifferenzaIngredienti();

                adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(getContext(), R.layout.lista_ingredienti_birra, listaIngredientiBirra, listaIngredientiDifferenza);
                fragmentPreparaBirraBinding.listViewIngredrientiPreparaBirra.setAdapter(adapterListViewIngredientiBirra);
                fragmentPreparaBirraBinding.listViewIngredrientiPreparaBirra.setDivider(null);
            }
        });

        fragmentPreparaBirraBinding.buttonRicettaPreparaBirra.setOnClickListener(v -> {
            birraViewModel.createBirra(new Birra(litriBirraScelti, ricetta.getId()));
            //Navigation
        });
        fragmentPreparaBirraBinding.textViewNomePreparaBirra.requestFocus();
        fragmentPreparaBirraBinding.textViewNomePreparaBirra.setError("Coglione");
    }

    public static double round(double n, int decimals) {
        return Math.floor(n * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    public void calcolaDifferenzaIngredienti(){
        for(int i=0; i < listaIngredientiBirra.size(); i++){
            int differenza = (int) Math.round(listaIngredientiDisponibili.get(i).getQuantitaPosseduta() - listaIngredientiBirra.get(0).getDosaggioIngrediente());
            Ingrediente ingrediente = new Ingrediente(listaIngredientiBirra.get(0).getTipoIngrediente(), differenza);
            listaIngredientiDifferenza.add(ingrediente);
        }
    }

}