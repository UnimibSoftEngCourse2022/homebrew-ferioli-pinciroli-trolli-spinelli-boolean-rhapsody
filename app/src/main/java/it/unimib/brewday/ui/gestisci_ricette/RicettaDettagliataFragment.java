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
import it.unimib.brewday.util.GestioneRicette;


public class RicettaDettagliataFragment extends Fragment {


    AdapterListViewRicettaDettagliata adapterListViewRicettaDettagliata;
    private FragmentRicettaDettagliataBinding fragmentRicettaDettagliataBinding;
    boolean visibile;
    private List<IngredienteRicetta> listaIngredientiRicetta;


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
        //ListView listViewIngredientiRicettaDettagliata = fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata;
        Button modificaRicettaButton = fragmentRicettaDettagliataBinding.buttonModificaRicetta;
        EditText numeroLitriRicettaBirra = fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra;
        EditText nomeRicetta = fragmentRicettaDettagliataBinding.textViewNomeRicetta;
        gestioneRicette = new GestioneRicette();
        Ricetta ricetta = RicettaDettagliataFragmentArgs.fromBundle(getArguments()).getRicetta();

        ricetteViewModel.getIngredientiRicetta(ricetta.getId());
        nomeRicetta.setText(ricetta.getNome());

        visibile = false;
        numeroLitriRicettaBirra.setEnabled(visibile);
        nomeRicetta.setEnabled(visibile);

        ricetteViewModel.getIngredientiRicetteRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                listaIngredientiRicetta = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();
                //setAdapterIngredienti(false, listaIngredientiRicetta);
                List<Ingrediente> ingredienti = new ArrayList<>();



                adapterListViewRicettaDettagliata = new AdapterListViewRicettaDettagliata(getContext(), 0, listaIngredientiRicetta,
                        R.layout.lista_ingredienti_singoli, new AdapterListViewRicettaDettagliata.OnItemClickListenerA() {
                    @Override
                    public void onAddIngredienteClick(IngredienteRicetta ingredienteRicetta) {

                    }

                    @Override
                    public void onRemoveIngredienteClick(IngredienteRicetta ingredienteRicetta) {

                    }
                }, ingredienteRicetta -> {
                }, true);
                fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setAdapter(adapterListViewRicettaDettagliata);
                fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setDivider(null);
            } else{
                Snackbar.make(view, "fdsa", Snackbar.LENGTH_LONG).show();
            }
        });




        //setAdapterIngredienti(visibile, listaIngredientiRicetta);


        modificaRicettaButton.setOnClickListener(v -> {
                    setVisibile(visibile, view, numeroLitriRicettaBirra, listaIngredientiRicetta);
                }
        );

        numeroLitriRicettaBirra.setOnFocusChangeListener((v, hasFocus) ->
                gestioneRicette.verificaNumeroLitriBirra(numeroLitriRicettaBirra, hasFocus)
        );


    }

    private void setVisibile(boolean invertiVisibile, View view, EditText numeroLitriBirra, List<IngredienteRicetta> listaIngredientiRicetta){

        if (!invertiVisibile){
            fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText(R.string.conferma);
            visibile = !invertiVisibile;
        } else {
            if( gestioneRicette.controlloCreazione(view, fragmentRicettaDettagliataBinding.textViewNomeRicetta, fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra)) {


                List<IngredienteRicetta> listaIngredientiPerLitro = new ArrayList<>();
                int zeroIngredinti = gestioneRicette.modificaListaIngredientiRicetta(listaIngredientiRicetta, listaIngredientiPerLitro, numeroLitriBirra);

                salvaRicetta(view, zeroIngredinti, listaIngredientiPerLitro);

                fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText(R.string.modifica);
                visibile = !invertiVisibile;
            }
        }

        fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra.setEnabled(visibile);
        fragmentRicettaDettagliataBinding.textViewNomeRicetta.setEnabled(visibile);
        //setAdapterIngredienti(visibile, listaIngredientiRicetta);

    }


    private void setAdapterIngredienti(boolean visible, List<IngredienteRicetta> listaIngredientiRicetta){
        /*adapterListViewRicettaDettagliata = new AdapterListViewRicettaDettagliata(getContext(),
                0, R.layout.lista_ingredienti_singoli, listaIngredientiRicetta, new AdapterListViewRicettaDettagliata.OnItemClickListenerA() {
            @Override
            public void onAddIngredienteClick(IngredienteRicetta ingredienteRicetta) {

            }

            @Override
            public void onRemoveIngredienteClick(IngredienteRicetta ingredienteRicetta) {

            }
        }, visible, new AdapterListViewRicettaDettagliata.OnFocusChangeListenerA() {
            @Override
            public void onChangeIngrediente(IngredienteRicetta ingredienteRicetta) {

            }
        });*/

        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setAdapter(adapterListViewRicettaDettagliata);
        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setDivider(null);
    }

    private void salvaRicetta(View view, int zeroIngredinti , List<IngredienteRicetta> listaIngredientiPerLitro) {
        if (zeroIngredinti < 3) {
            //TODO updateRicetta
            setAdapterIngredienti(false,  listaIngredientiPerLitro);

        } else {
            Snackbar.make(view, R.string.ingredienti_ricetta_mancanti, LENGTH_SHORT).show();
        }
    }


}
