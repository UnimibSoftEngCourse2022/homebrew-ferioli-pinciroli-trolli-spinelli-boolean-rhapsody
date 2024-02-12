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
import java.util.concurrent.atomic.AtomicReference;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentPreparaBirraBinding;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.ui.gestisci_ricette.RicetteViewModel;
import it.unimib.brewday.ui.gestisci_ricette.RicetteViewModelFactory;


public class PreparaBirraFragment extends Fragment {

    private FragmentPreparaBirraBinding fragmentPreparaBirraBinding;
    private RicetteViewModel ricetteViewModel;

    List<IngredienteRicetta> listaIngredientiBirra = new ArrayList<>();

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
                new RicetteViewModelFactory(getContext()))
                .get(RicetteViewModel.class);
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

        /*listaIngredientiBirra.get().add(new IngredienteRicetta(ricetta.getId() ,TipoIngrediente.ACQUA, 0.0));
        listaIngredientiBirra.get().add(new IngredienteRicetta(ricetta.getId() ,TipoIngrediente.ADDITIVI, 0.0));
        listaIngredientiBirra.get().add(new IngredienteRicetta(ricetta.getId() ,TipoIngrediente.LUPPOLO, 0.0));
        listaIngredientiBirra.get().add(new IngredienteRicetta(ricetta.getId() ,TipoIngrediente.MALTO, 0.0));
        listaIngredientiBirra.get().add(new IngredienteRicetta(ricetta.getId() ,TipoIngrediente.ZUCCHERO, 0.0));
        listaIngredientiBirra.get().add(new IngredienteRicetta(ricetta.getId() ,TipoIngrediente.LIEVITI, 0.0));
*/

            ricetteViewModel.getIngredientiRicetteRisultato().observe(getViewLifecycleOwner(), risultato ->  {
                if (risultato.isSuccessful()){
                    listaIngredientiBirra = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();
                    for (IngredienteRicetta ingredienteRicetta : listaIngredientiBirra) {
                        ingredienteRicetta.setDosaggioIngrediente(ingredienteRicetta.getDosaggioIngrediente()*litriBirraScelti);
                    }
                adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(getContext(), R.layout.lista_ingredienti_birra, listaIngredientiBirra);
            } else {
                Snackbar.make(view, "non riesco a recuperare gli ingredienti", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        //adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(getContext(), R.layout.lista_ingredienti_birra, listaIngredientiBirra);

    }
}