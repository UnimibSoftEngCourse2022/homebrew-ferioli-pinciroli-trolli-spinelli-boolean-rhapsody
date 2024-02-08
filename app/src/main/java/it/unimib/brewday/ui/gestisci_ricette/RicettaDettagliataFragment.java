package it.unimib.brewday.ui.gestisci_ricette;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentRicettaDettagliataBinding;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.ui.gestisci_ingredienti.AdapterListViewListaIngredientiDisponibili;
import it.unimib.brewday.util.ListaIngredienti;


public class RicettaDettagliataFragment extends Fragment {

    List<Ingrediente> listaIngredientiRicetta;
    AdapterListViewListaIngredientiDisponibili adapterListViewListaIngredientiRicetta;
    private FragmentRicettaDettagliataBinding fragmentRicettaDettagliataBinding;
    boolean visibile;
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
        ListView listViewIngredientiRicettaDettagliata = fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata;
        Button modificaRicettaButton = fragmentRicettaDettagliataBinding.buttonModificaRicetta;
        EditText numeroLitriRicettaBirra = fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra;

        ListaIngredienti listaIngredienti = new ListaIngredienti();
        listaIngredientiRicetta = listaIngredienti.getListaIngredienti();

        visibile = false;

        setAdapterIngredienti(visibile);
        numeroLitriRicettaBirra.setFocusableInTouchMode(visibile);

        modificaRicettaButton.setOnClickListener(v -> {
            setVisibile(visibile);
        });


    }

    public void setVisibile(boolean invertiVisibile){
        visibile = !invertiVisibile;
        setAdapterIngredienti(visibile);
        fragmentRicettaDettagliataBinding.editTextNumberLitriRicettaBirra.setFocusableInTouchMode(visibile);
        if (visibile){
            fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText("Conferma");
        } else {
            fragmentRicettaDettagliataBinding.buttonModificaRicetta.setText("Modifica");
        }

    }

    public void setAdapterIngredienti(boolean visible){
        adapterListViewListaIngredientiRicetta = new AdapterListViewListaIngredientiDisponibili(
                getContext(), 0, listaIngredientiRicetta, R.layout.lista_ingredienti_singoli,
                new AdapterListViewListaIngredientiDisponibili.OnItemClickListener() {
                    @Override
                    public void onAddIngredienteClick(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {


                    }

                    @Override
                    public void onRemoveIngredienteClick(Ingrediente ingrediente, int position, EditText quantitaIngrediente) {

                    }
                }, (ingrediente, quantitaIngrediente, position) -> {


        }, visible);

        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setAdapter(adapterListViewListaIngredientiRicetta);
        fragmentRicettaDettagliataBinding.listViewIngredrientiRicettaDettagliata.setDivider(null);
    }
}