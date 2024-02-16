package it.unimib.brewday.ui.gestione_birra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentBirraDettagliataBinding;
import it.unimib.brewday.databinding.FragmentPreparaBirraBinding;
import it.unimib.brewday.model.Birra;

public class BirraDettagliataFragment extends Fragment {
    private FragmentBirraDettagliataBinding fragmentBirraDettagliataBinding;
    private AdapterListViewIngredientiBirra adapterListViewIngredientiBirra;
    private BirraViewModel birraViewModel;

    public BirraDettagliataFragment() {
        // Required empty public constructor
    }

    public static BirraDettagliataFragment newInstance() {
        return new BirraDettagliataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        birraViewModel = new ViewModelProvider(this,
                new BirraViewModelFactory(getContext()))
                .get(BirraViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentBirraDettagliataBinding = FragmentBirraDettagliataBinding
                .inflate(inflater, container,false);
        return fragmentBirraDettagliataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Birra birraSelezionata = BirraDettagliataFragmentArgs.fromBundle(getArguments()).getBirra();


            birraViewModel.getIngredientiBirra(birraSelezionata);

            birraViewModel.getIngredientiBirraRisultato().observe(getViewLifecycleOwner(), risultato -> {

                adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(
                        requireContext(),
                        R.layout.lista_ingredienti_birra,
                        listaIngredientiBirra,
                        listaDifferenzaIngredienti);

                fragmentBirraDettagliataBinding.listViewIgredientiBirraDettagliata.setAdapter(adapterListViewIngredientiBirra);
                fragmentBirraDettagliataBinding.listViewIgredientiBirraDettagliata.setDivider(null);

            });

    }
}