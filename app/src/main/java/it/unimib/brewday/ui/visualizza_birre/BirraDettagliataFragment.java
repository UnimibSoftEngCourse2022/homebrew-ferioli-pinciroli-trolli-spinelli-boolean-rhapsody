package it.unimib.brewday.ui.visualizza_birre;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentBirraDettagliataBinding;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.BirraConRicetta;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.gestione_birra.AdapterListViewIngredientiBirra;
import it.unimib.brewday.ui.gestione_birra.BirraViewModel;
import it.unimib.brewday.ui.gestione_birra.BirraViewModelFactory;
import it.unimib.brewday.ui.gestisci_attrezzi.AdapterRecyclerViewAttrezzi;
import it.unimib.brewday.ui.gestisci_attrezzi.InserisciAttrezzoDialog;

public class BirraDettagliataFragment extends Fragment {
    private FragmentBirraDettagliataBinding fragmentBirraDettagliataBinding;
    private AdapterListViewIngredientiBirra adapterListViewIngredientiBirra;
    private VisualizzaBirreViewModel visualizzaBirreViewModel;

    private AdapterRecyclerViewAttrezzi adapterRecyclerViewAttrezzi;

    public BirraDettagliataFragment() {
        // Required empty public constructor
    }

    public static BirraDettagliataFragment newInstance() {
        return new BirraDettagliataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visualizzaBirreViewModel = new ViewModelProvider(this,
                new VisualizzaBirreViewModelFactory(getContext()))
                .get(VisualizzaBirreViewModel.class);
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);

        BirraConRicetta birraSelezionata = BirraDettagliataFragmentArgs.fromBundle(getArguments()).getBirra();
        fragmentBirraDettagliataBinding.textViewNumeroLitriBirraDettagliata.setText(Integer.toString(birraSelezionata.getLitriProdotti()));
        fragmentBirraDettagliataBinding.textViewNomeBirraDettagliata.setText(birraSelezionata.getNomeRicetta());

            visualizzaBirreViewModel.getIngredientiBirra(birraSelezionata);

            visualizzaBirreViewModel.getIngredientiBirraRisultato().observe(getViewLifecycleOwner(), risultato -> {
                if (risultato.isSuccessful()) {
                    List <IngredienteRicetta> listaIngredientiBirra = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();

                    adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(
                            requireContext(),
                            R.layout.lista_ingredienti_birra,
                            listaIngredientiBirra,
                            new ArrayList<>());

                    fragmentBirraDettagliataBinding.listViewIgredientiBirraDettagliata.setAdapter(adapterListViewIngredientiBirra);
                    fragmentBirraDettagliataBinding.listViewIgredientiBirraDettagliata.setDivider(null);

                }
            });
            if(birraSelezionata.isTerminata()) {
                fragmentBirraDettagliataBinding.textViewAttrezziBirraDettagliata.setVisibility(View.GONE);
            }else{
                visualizzaBirreViewModel.getAttrezziBirra(birraSelezionata);
            }

            visualizzaBirreViewModel.getAttrezziBirraRisultato().observe(getViewLifecycleOwner(), risultato -> {
                    if(risultato.isSuccessful()) {
                        List<Attrezzo> listaAttrezziBirra = ((Risultato.ListaAttrezziSuccesso)risultato).getAttrezzi();
                        adapterRecyclerViewAttrezzi = new AdapterRecyclerViewAttrezzi(listaAttrezziBirra, null, true);
                        fragmentBirraDettagliataBinding.recyclerViewAttrezziBirraDettagliata.setLayoutManager(layoutManager);
                        fragmentBirraDettagliataBinding.recyclerViewAttrezziBirraDettagliata.setAdapter(adapterRecyclerViewAttrezzi);

                    }
            });

            fragmentBirraDettagliataBinding.imageButtonNuovaNotaDegustazione.setOnClickListener(v -> {
                NoteDegustazioneDialog dialog = new NoteDegustazioneDialog();
                dialog.show(getParentFragmentManager(), "Inserisci nuova Nota Degustazione");
            });


    }
}