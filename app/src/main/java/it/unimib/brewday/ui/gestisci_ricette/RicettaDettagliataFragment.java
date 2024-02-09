package it.unimib.brewday.ui.gestisci_ricette;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import it.unimib.brewday.ui.gestisci_ingredienti.AdapterListViewListaIngredientiDisponibili;
import it.unimib.brewday.util.GestioneRicette;
import it.unimib.brewday.util.ListaIngredienti;


public class RicettaDettagliataFragment extends Fragment {


    AdapterListViewListaIngredientiDisponibili adapterListViewListaIngredientiRicetta;
    private FragmentRicettaDettagliataBinding fragmentRicettaDettagliataBinding;
    boolean visibile;

    GestioneRicette gestioneRicette;

    public RicettaDettagliataFragment() {
        // Required empty public constructor
    }


    public static RicettaDettagliataFragment newInstance() {
        return new RicettaDettagliataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        ListaIngredienti listaIngredienti = new ListaIngredienti();
        List<Ingrediente> listaIngredientiRicetta = listaIngredienti.getListaIngredienti();

        visibile = false;

        setAdapterIngredienti(visibile, listaIngredientiRicetta);
        numeroLitriRicettaBirra.setEnabled(visibile);
        nomeRicetta.setEnabled(visibile);

        modificaRicettaButton.setOnClickListener(v ->
                setVisibile(visibile, view, numeroLitriRicettaBirra, listaIngredientiRicetta)
        );

        numeroLitriRicettaBirra.setOnFocusChangeListener((v, hasFocus) ->
                gestioneRicette.verificaNumeroLitriBirra(numeroLitriRicettaBirra, hasFocus)
        );


}

    public void setVisibile(boolean invertiVisibile, View view, EditText numeroLitriBirra, List<Ingrediente> listaIngredientiRicetta){
        if (!invertiVisibile){
            fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText(R.string.conferma);
            visibile = !invertiVisibile;
        } else {
            if( gestioneRicette.controlloCreazione(view, fragmentRicettaDettagliataBinding.textViewNomeRicetta, fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra)) {


                List<Double> listaIngredientiPerLitro = new ArrayList<>();
                int zeroIngredinti = gestioneRicette.creaListaIngredientiRicetta(listaIngredientiRicetta, listaIngredientiPerLitro, numeroLitriBirra);

                salvaRicetta(view, zeroIngredinti, listaIngredientiRicetta, listaIngredientiPerLitro);

                fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText(R.string.modifica);
                visibile = !invertiVisibile;
            }
        }

        fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra.setEnabled(visibile);
        fragmentRicettaDettagliataBinding.textViewNomeRicetta.setEnabled(visibile);
        setAdapterIngredienti(visibile, listaIngredientiRicetta);

    }

    public void setAdapterIngredienti(boolean visible, List<Ingrediente> listaIngredientiRicetta){
        adapterListViewListaIngredientiRicetta = new AdapterListViewListaIngredientiDisponibili(
                getContext(), 0, listaIngredientiRicetta, R.layout.lista_ingredienti_singoli,
                new AdapterListViewListaIngredientiDisponibili.OnItemClickListener() {
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

        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setAdapter(adapterListViewListaIngredientiRicetta);
        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setDivider(null);
    }

    public void salvaRicetta(View view, int zeroIngredinti , List<Ingrediente> listaIngredientiRicetta, List<Double> listaIngredientiPerLitro) {
        if (zeroIngredinti < 3) {
            //TODO chiamata luca con listaIngredientiPerLitro
            setAdapterIngredienti(false,  listaIngredientiRicetta);

        } else {
            Snackbar.make(view, R.string.ingredienti_ricetta_mancanti, LENGTH_SHORT).show();
        }
    }


}
