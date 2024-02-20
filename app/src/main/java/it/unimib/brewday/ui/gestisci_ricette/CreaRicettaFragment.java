package it.unimib.brewday.ui.gestisci_ricette;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import it.unimib.brewday.databinding.FragmentCreaRicettaBinding;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.gestisci_ingredienti.AdapterListViewIngredienti;
import it.unimib.brewday.model.ListaIngredienti;
import it.unimib.brewday.util.RegistroErrori;

public class CreaRicettaFragment extends Fragment {

    private  FragmentCreaRicettaBinding fragmentCreaRicettaBinding;

    List<Ingrediente> listaIngredientiRicetta;

    RicetteViewModel ricettaViewModel;




    public CreaRicettaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ricettaViewModel = new ViewModelProvider(this,
                new RicetteViewModelFactory(getContext()))
                .get(RicetteViewModel.class);
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

        AdapterListViewIngredienti adapterListViewListaIngredientiRicetta = new AdapterListViewIngredienti(
                getContext(), 0, listaIngredientiRicetta, R.layout.lista_ingredienti_singoli,
                new AdapterListViewIngredienti.OnItemClickListener() {
                    @Override
                    public void onAddIngredienteClick(Ingrediente ingrediente) {
                                //vuoto
                    }

                    @Override
                    public void onRemoveIngredienteClick(Ingrediente ingrediente) {
                            //vuoto
                    }
                }, ingrediente -> {
                   //vuoto
        }, true);

        ricettaViewModel.getInsertRicettaRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                getParentFragmentManager().popBackStackImmediate();
            }
            else{
                String errore = ((Risultato.Errore) risultato).getMessaggio();
                Snackbar.make(view, getString(RegistroErrori.getInstance().getErrore(errore)), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        listViewIngredientiRicetta.setAdapter(adapterListViewListaIngredientiRicetta);
        listViewIngredientiRicetta.setDivider(null);

        numeroLitriBirra.setOnFocusChangeListener((v, hasFocus) ->
                RicetteUtil.verificaNumeroLitriBirra(numeroLitriBirra, hasFocus)
        );

        creaRicettaButton.setOnClickListener(v -> {
            if(RicetteUtil.controlloCreazione(view, nomeRicetta, numeroLitriBirra)){
                List<IngredienteRicetta> listaIngredientiPerLitro = new ArrayList<>();
                int zeroIngredienti = RicetteUtil.creaListaIngredientiRicetta(listaIngredientiRicetta, listaIngredientiPerLitro, numeroLitriBirra);
                salvaRicetta(view, zeroIngredienti, listaIngredientiPerLitro, new Ricetta(nomeRicetta.getText().toString(),Integer.parseInt(numeroLitriBirra.getText().toString())));
            }
        });
    }


    private void salvaRicetta(View view, int zeroIngredienti, List<IngredienteRicetta> ingredientiRicetta, Ricetta ricetta) {
        if (zeroIngredienti < 3) {
            ricettaViewModel.insertRicetta(ricetta, ingredientiRicetta);
        }
        else{
            Snackbar.make(view, R.string.ingredienti_ricetta_mancanti, LENGTH_SHORT).show();
        }
    }



}