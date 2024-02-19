package it.unimib.brewday.ui.visualizza_birre;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentBirraDettagliataBinding;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.BirraConRicetta;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.NotaDegustazione;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.gestione_birra.AdapterListViewIngredientiBirra;
import it.unimib.brewday.ui.gestisci_attrezzi.AdapterRecyclerViewAttrezzi;
import it.unimib.brewday.util.RegistroErrori;

public class BirraDettagliataFragment extends Fragment {
    private FragmentBirraDettagliataBinding fragmentBirraDettagliataBinding;
    private AdapterListViewIngredientiBirra adapterListViewIngredientiBirra;
    private VisualizzaBirreViewModel visualizzaBirreViewModel;

    private AdapterRecyclerViewAttrezzi adapterRecyclerViewAttrezzi;

    private AdapterRecyclerViewNoteDegustazione adapterRecyclerViewNoteDegustazione;

    public BirraDettagliataFragment() {
        // Required empty public constructor
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

        RecyclerView.LayoutManager layoutManagerAttrezzi = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);

        BirraConRicetta birraSelezionata = BirraDettagliataFragmentArgs.fromBundle(getArguments()).getBirra();

        fragmentBirraDettagliataBinding.textViewNumeroLitriBirraDettagliata.setText(Integer.toString(birraSelezionata.getLitriProdotti()));
        fragmentBirraDettagliataBinding.textViewNomeBirraDettagliata.setText(birraSelezionata.getNomeRicetta());
        if(!birraSelezionata.getNotaGenerale().equals("")) {
            fragmentBirraDettagliataBinding.inputTextLayoutNoteBirraDettagliata.getEditText().setText(birraSelezionata.getNotaGenerale());
        }

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
            else{
                String errore = ((Risultato.Errore) risultato).getMessaggio();
                Snackbar.make(view, getString(RegistroErrori.getInstance().getErrore(errore)), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        visualizzaBirreViewModel.getAttrezziBirraRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()) {
                List<Attrezzo> listaAttrezziBirra = ((Risultato.ListaAttrezziSuccesso)risultato).getAttrezzi();
                adapterRecyclerViewAttrezzi = new AdapterRecyclerViewAttrezzi(listaAttrezziBirra, null, true);
                fragmentBirraDettagliataBinding.recyclerViewAttrezziBirraDettagliata.setLayoutManager(layoutManagerAttrezzi);
                fragmentBirraDettagliataBinding.recyclerViewAttrezziBirraDettagliata.setAdapter(adapterRecyclerViewAttrezzi);
            }
            else{
                String errore = ((Risultato.Errore) risultato).getMessaggio();
                Snackbar.make(view, getString(RegistroErrori.getInstance().getErrore(errore)), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        visualizzaBirreViewModel.getUpdateBirreRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (!risultato.isSuccessful()){
                String errore = ((Risultato.Errore) risultato).getMessaggio();
                Snackbar.make(view, getString(RegistroErrori.getInstance().getErrore(errore)), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        visualizzaBirreViewModel.getNoteDegustazioneRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                RecyclerView.LayoutManager layoutManagerNote = new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);
                List<NotaDegustazione> listaNoteDegustazione = ((Risultato.AllNoteDegustazioneSuccesso) risultato).getListaNoteDegustazione();
                adapterRecyclerViewNoteDegustazione = new AdapterRecyclerViewNoteDegustazione(listaNoteDegustazione);
                fragmentBirraDettagliataBinding.recyclerViewNoteDettagliate.setLayoutManager(layoutManagerNote);
                fragmentBirraDettagliataBinding.recyclerViewNoteDettagliate.setAdapter(adapterRecyclerViewNoteDegustazione);
            }else{
                String errore = ((Risultato.Errore) risultato).getMessaggio();
                Snackbar.make(view, getString(RegistroErrori.getInstance().getErrore(errore)), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        visualizzaBirreViewModel.getIngredientiBirra(birraSelezionata);

        if(birraSelezionata.isTerminata()) {
            fragmentBirraDettagliataBinding.textViewAttrezziBirraDettagliata.setVisibility(View.GONE);
            visualizzaBirreViewModel.getNoteDegustazione(birraSelezionata.getId());
        }else{
            visualizzaBirreViewModel.getAttrezziBirra(birraSelezionata);
            fragmentBirraDettagliataBinding.imageButtonNuovaNotaDegustazione.setVisibility(View.GONE);
            fragmentBirraDettagliataBinding.textViewNoteDiDegustazione.setVisibility(View.GONE);
        }

        fragmentBirraDettagliataBinding.imageButtonNuovaNotaDegustazione.setOnClickListener(v -> {
            NoteDegustazioneDialog dialog = new NoteDegustazioneDialog(visualizzaBirreViewModel, birraSelezionata);
            dialog.show(getParentFragmentManager(), "Inserisci nuova nota di degustazione");

        });

        fragmentBirraDettagliataBinding.editTextNoteBirraDettagliata.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // method is empty
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                birraSelezionata.setNotaGenerale(fragmentBirraDettagliataBinding.editTextNoteBirraDettagliata.getText().toString());
                visualizzaBirreViewModel.updateBirra(birraSelezionata);
            }
            @Override
            public void afterTextChanged(Editable s) {
                // method is empty
            }
        });
    }
}