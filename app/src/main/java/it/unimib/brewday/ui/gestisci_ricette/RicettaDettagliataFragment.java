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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentRicettaDettagliataBinding;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.gestisci_ingredienti.AdapterListViewListaIngredientiDisponibili;
import it.unimib.brewday.util.GestioneRicette;


public class RicettaDettagliataFragment extends Fragment {


    AdapterListViewListaIngredientiDisponibili adapterListViewListaIngredientiDisponibili;
    private FragmentRicettaDettagliataBinding fragmentRicettaDettagliataBinding;
    boolean visibile;
    private List<IngredienteRicetta> listaIngredientiRicettaGL;

    private List<Ingrediente> listaIngredientiRicetta = new ArrayList<>();


    GestioneRicette gestioneRicette;
    private RicetteViewModel ricetteViewModel;

    public RicettaDettagliataFragment() {
        // Required empty public constructor
    }


    public static RicettaDettagliataFragment newInstance() {
        return new RicettaDettagliataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ricetteViewModel = new ViewModelProvider(this,
                new RicetteViewModelFactory(getContext()))
                .get(RicetteViewModel.class);
        listaIngredientiRicetta = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRicettaDettagliataBinding = FragmentRicettaDettagliataBinding.inflate(inflater, container, false);
        return fragmentRicettaDettagliataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button modificaRicettaButton = fragmentRicettaDettagliataBinding.buttonModificaRicetta;
        EditText numeroLitriRicettaBirra = fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra;
        EditText nomeRicetta = fragmentRicettaDettagliataBinding.textViewNomeRicetta;
        gestioneRicette = new GestioneRicette();
        Ricetta ricetta = RicettaDettagliataFragmentArgs.fromBundle(getArguments()).getRicetta();

        ricetteViewModel.getIngredientiRicetta(ricetta.getId());
        nomeRicetta.setText(ricetta.getNome());
        numeroLitriRicettaBirra.setText(Integer.toString(ricetta.getLitriDiRiferimento()));

        visibile = false;
        numeroLitriRicettaBirra.setEnabled(visibile);
        nomeRicetta.setEnabled(visibile);

        ricetteViewModel.getIngredientiRicetteRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                listaIngredientiRicettaGL = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();
                for (IngredienteRicetta ingredienteRicetta: listaIngredientiRicettaGL) {
                    listaIngredientiRicetta.add(new Ingrediente(ingredienteRicetta.getTipoIngrediente(), (int)ingredienteRicetta.getDosaggioIngrediente() * ricetta.getLitriDiRiferimento()));
                }
                setAdapterIngredienti(false, listaIngredientiRicetta);
            } else{
                Snackbar.make(view, "fdsa", Snackbar.LENGTH_LONG).show();
            }
        });






        modificaRicettaButton.setOnClickListener(v ->
                    setVisibile(visibile, view, nomeRicetta, numeroLitriRicettaBirra, listaIngredientiRicetta, ricetta)

        );

        numeroLitriRicettaBirra.setOnFocusChangeListener((v, hasFocus) ->
                gestioneRicette.verificaNumeroLitriBirra(numeroLitriRicettaBirra, hasFocus)
        );


    }

    private void setVisibile(boolean invertiVisibile, View view,EditText nomeRicetta , EditText numeroLitriBirra, List<Ingrediente> listaIngredientiRicetta, Ricetta ricetta){

        if (!invertiVisibile){
            fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText(R.string.conferma);
            visibile = !invertiVisibile;
        } else {
            if( gestioneRicette.controlloCreazione(view, fragmentRicettaDettagliataBinding.textViewNomeRicetta, fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra)) {



                int zeroIngredinti = gestioneRicette.creaListaIngredientiRicetta(listaIngredientiRicetta, listaIngredientiRicettaGL, numeroLitriBirra, ricetta.getId());

                salvaRicetta(view, zeroIngredinti, listaIngredientiRicettaGL, listaIngredientiRicetta);
                ricetta.setLitriDiRiferimento(Integer.parseInt(numeroLitriBirra.getText().toString()));
                ricetta.setNome(nomeRicetta.getText().toString());
                ricetteViewModel.updateRicetta(ricetta);

                fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText(R.string.modifica);
                visibile = !invertiVisibile;
            }
        }

        fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra.setEnabled(visibile);
        fragmentRicettaDettagliataBinding.textViewNomeRicetta.setEnabled(visibile);
        setAdapterIngredienti(visibile, listaIngredientiRicetta);

    }


    private void setAdapterIngredienti(boolean visible, List<Ingrediente> listaIngredientiRicetta){
        adapterListViewListaIngredientiDisponibili = new AdapterListViewListaIngredientiDisponibili(getContext(),
                0, listaIngredientiRicetta, R.layout.lista_ingredienti_singoli, new AdapterListViewListaIngredientiDisponibili.OnItemClickListener() {

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
        }, visible);

        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setAdapter(adapterListViewListaIngredientiDisponibili);
        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setDivider(null);
    }

    private void salvaRicetta(View view, int zeroIngredinti , List<IngredienteRicetta> listaIngredientiRicettaGL, List<Ingrediente> listaIngredientiRicetta) {
        if (zeroIngredinti < 3) {
            ricetteViewModel.updateIngredientiRicetta(listaIngredientiRicettaGL);
            setAdapterIngredienti(false,  listaIngredientiRicetta);

        } else {
            Snackbar.make(view, R.string.ingredienti_ricetta_mancanti, LENGTH_SHORT).show();
        }
    }


}
