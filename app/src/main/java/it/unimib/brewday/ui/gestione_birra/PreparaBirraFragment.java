package it.unimib.brewday.ui.gestione_birra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentPreparaBirraBinding;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;

public class PreparaBirraFragment extends Fragment {

    private FragmentPreparaBirraBinding fragmentPreparaBirraBinding;
    private BirraViewModel birraViewModel;
    List<IngredienteRicetta> listaIngredientiBirra;
    List<Ingrediente> listaIngredientiDisponibili;
    List<Attrezzo> listaAttrezziDisponibili;
    List<Integer> listaDifferenzaIngredienti;
    List<Attrezzo> listaAttrezziSelezionati;
    private boolean possiedeAttrezzi;

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

        birraViewModel = new ViewModelProvider(this,
                new BirraViewModelFactory(getContext())).get(BirraViewModel.class);

        listaIngredientiBirra = new ArrayList<>();
        listaIngredientiDisponibili = new ArrayList<>();
        listaAttrezziDisponibili = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentPreparaBirraBinding = FragmentPreparaBirraBinding
                .inflate(inflater, container,false);
        return fragmentPreparaBirraBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
         * Ottengo i dati dai safe args ed inizializzo alcuni elementi della schermata (nome)
         */
        Ricetta ricetta = PreparaBirraFragmentArgs.fromBundle(getArguments()).getRicetta();
        int litriBirraScelti = PreparaBirraFragmentArgs.fromBundle(getArguments()).getNumeroLitriBirraScelti();
        possiedeAttrezzi = false;
        fragmentPreparaBirraBinding.textViewNomePreparaBirra.setText(ricetta.getNome());

        /*
         * Osservo il risultato del recupero degli ingredienti della ricetta moltiplicati per il numero di litri
         */
        birraViewModel.getIngredientiRicettaPerLitriRisultato().observe(getViewLifecycleOwner(), risultato ->  {
            if (risultato.isSuccessful()){
                listaIngredientiBirra = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();
            } else {
                Snackbar.make(view, "Errore nel recupero e calcolo degli ingredienti", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        /*
         * Osservo il risultato della differenza tra gli ingredienti che ci sono a disposizione e
         * quelli richiesti per preparare i litri di birra specificati. (Serve per far comparire
         * a schermo gli errori)
         */
        birraViewModel.getDifferenzaIngredientiRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()) {
                listaDifferenzaIngredienti = ((Risultato.ListaDifferenzaIngredientiSuccesso) risultato).getListaDifferenzaIngredienti();

                adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(
                        getContext(),
                        R.layout.lista_ingredienti_birra,
                        listaIngredientiBirra,
                        listaDifferenzaIngredienti);

                fragmentPreparaBirraBinding.listViewIngredrientiPreparaBirra.setAdapter(adapterListViewIngredientiBirra);
                fragmentPreparaBirraBinding.listViewIngredrientiPreparaBirra.setDivider(null);
            }
        });

        birraViewModel.getCreateBirraResult().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                Snackbar.make(view, "Hai creato una nuova Birra!!", BaseTransientBottomBar.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else{
                Snackbar.make(view, ((Risultato.Errore) risultato).getMessaggio(), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        birraViewModel.getAttrezziSelezionatiRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                possiedeAttrezzi = true;
                listaAttrezziSelezionati = ((Risultato.ListaAttrezziSuccesso) risultato).getAttrezzi();
            }
            else{
                if(risultato instanceof Risultato.ErroreConSuggerimentoLitri){
                    //gestire il suggerimento
                }
                else{
                    //TODO: gestione errore
                }
            }
        });


        birraViewModel.getDifferenzaIngredienti(ricetta.getId(), litriBirraScelti);
        birraViewModel.readAndOptimizeAttrezziLiberi(litriBirraScelti);

        fragmentPreparaBirraBinding.buttonRicettaPreparaBirra.setOnClickListener(v -> {

            if (verificaIngredienti()){
                if (possiedeAttrezzi){
                    birraViewModel.createBirra(new Birra(litriBirraScelti, ricetta.getId()), listaDifferenzaIngredienti, listaAttrezziSelezionati);

                } else {
                    Snackbar.make(view, "Gli attrezzi sono troppo piccoli", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(view, "Attenzione ti mancano degli ingredienti", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

    }


    public boolean verificaIngredienti(){
        for (int i = 0; i < listaDifferenzaIngredienti.size(); i++) {
            if (listaDifferenzaIngredienti.get(i) < 0){
                return false;
            }
        }
        return  true;
    }

}
