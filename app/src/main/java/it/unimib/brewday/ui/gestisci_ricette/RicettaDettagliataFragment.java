package it.unimib.brewday.ui.gestisci_ricette;

import static android.widget.Toast.LENGTH_SHORT;

import static it.unimib.brewday.ui.Topbar.gestisciTopbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentRicettaDettagliataBinding;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.gestione_birra.InserisciLitriDialog;
import it.unimib.brewday.ui.gestisci_ingredienti.AdapterListViewIngredienti;


public class RicettaDettagliataFragment extends Fragment {


    AdapterListViewIngredienti adapterListViewIngredienti;
    private FragmentRicettaDettagliataBinding fragmentRicettaDettagliataBinding;
    boolean visibile;
    private List<IngredienteRicetta> listaIngredientiRicettaGL;

    private List<Ingrediente> listaIngredientiRicetta = new ArrayList<>();

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

        //Gestione Topbar
        gestisciTopbar((AppCompatActivity) requireActivity());


        ImageButton modificaRicettaButton = fragmentRicettaDettagliataBinding.imageButtonModificaRicetta;
        EditText numeroLitriRicettaBirra = fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra;
        EditText nomeRicetta = fragmentRicettaDettagliataBinding.textViewNomeRicettaDettagliata;
        ImageButton preparaBirra = fragmentRicettaDettagliataBinding.imageButtonRicettaDettagliataPrepara;
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
                listaIngredientiRicetta.clear();
                for (IngredienteRicetta ingredienteRicetta: listaIngredientiRicettaGL) {
                    listaIngredientiRicetta.add(new Ingrediente(ingredienteRicetta.getTipoIngrediente(),
                            (int) (ingredienteRicetta.getDosaggioIngrediente() * ricetta.getLitriDiRiferimento())));
                }
                setAdapterIngredienti(false, listaIngredientiRicetta);
            } else{
                Snackbar.make(view, "non riesco a recuperare gli ingredienti", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        ricetteViewModel.getUpdateIngredientiRicettaRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                setAdapterIngredienti(false,  listaIngredientiRicetta);
            } else {
                Snackbar.make(view, "Errore, ingredienti non aggiornati correttamente", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        ricetteViewModel.getUpdateRicettaRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (!risultato.isSuccessful()){
                Snackbar.make(view, "Errore, ricetta non aggiornata correttamente", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });


        modificaRicettaButton.setOnClickListener(v ->
                setVisibile(visibile, view, nomeRicetta, numeroLitriRicettaBirra, listaIngredientiRicetta, ricetta)

        );

        preparaBirra.setOnClickListener(v -> {
            InserisciLitriDialog litriDialog = new InserisciLitriDialog(ricetta, view, false);
            litriDialog.show(getParentFragmentManager(), "Scegli litri birra da preparare");
        });

        numeroLitriRicettaBirra.setOnFocusChangeListener((v, hasFocus) ->
                RicetteUtil.verificaNumeroLitriBirra(numeroLitriRicettaBirra, hasFocus)
        );
    }

    private void setVisibile(boolean invertiVisibile, View view,EditText nomeRicetta , EditText numeroLitriBirra, List<Ingrediente> listaIngredientiRicetta, Ricetta ricetta){

        if (!invertiVisibile){
            fragmentRicettaDettagliataBinding.imageButtonModificaRicetta.setImageResource(R.drawable.check_circle_24px);
            fragmentRicettaDettagliataBinding.imageButtonRicettaDettagliataPrepara.setVisibility(View.GONE);
            visibile = !invertiVisibile;
        } else {
            if(RicetteUtil.controlloCreazione(view, fragmentRicettaDettagliataBinding.textViewNomeRicettaDettagliata,
                    fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra)) {

                int zeroIngredinti = RicetteUtil.creaListaIngredientiRicetta(listaIngredientiRicetta, listaIngredientiRicettaGL, numeroLitriBirra, ricetta.getId());

                salvaRicetta(view, zeroIngredinti, listaIngredientiRicettaGL);
                ricetta.setLitriDiRiferimento(Integer.parseInt(numeroLitriBirra.getText().toString()));
                ricetta.setNome(nomeRicetta.getText().toString());
                ricetteViewModel.updateRicetta(ricetta);

                fragmentRicettaDettagliataBinding.imageButtonModificaRicetta.setImageResource(R.drawable.edit_24px);
                fragmentRicettaDettagliataBinding.imageButtonRicettaDettagliataPrepara.setVisibility(View.VISIBLE);
                visibile = !invertiVisibile;
            }
        }

        fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra.setEnabled(visibile);
        fragmentRicettaDettagliataBinding.textViewNomeRicettaDettagliata.setEnabled(visibile);
        setAdapterIngredienti(visibile, listaIngredientiRicetta);

    }


    private void setAdapterIngredienti(boolean visible, List<Ingrediente> listaIngredientiRicetta){
        adapterListViewIngredienti = new AdapterListViewIngredienti(getContext(),
                0, listaIngredientiRicetta, R.layout.lista_ingredienti_singoli, new AdapterListViewIngredienti.OnItemClickListener() {

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

        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setAdapter(adapterListViewIngredienti);
        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setDivider(null);
    }

    private void salvaRicetta(View view, int zeroIngredinti , List<IngredienteRicetta> listaIngredientiRicettaGL) {
        if (zeroIngredinti < 3) {
            ricetteViewModel.updateIngredientiRicetta(listaIngredientiRicettaGL);
        } else {
            Snackbar.make(view, R.string.ingredienti_ricetta_mancanti, LENGTH_SHORT).show();
        }
    }

}
