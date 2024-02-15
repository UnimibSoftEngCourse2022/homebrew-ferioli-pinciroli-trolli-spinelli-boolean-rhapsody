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
import it.unimib.brewday.ui.gestisci_attrezzi.AttrezziViewModel;
import it.unimib.brewday.ui.gestisci_attrezzi.AttrezziViewModelFactory;
import it.unimib.brewday.ui.gestisci_ricette.RicetteViewModel;
import it.unimib.brewday.ui.gestisci_ricette.RicetteViewModelFactory;
import it.unimib.brewday.util.Ottimizzazione;


public class PreparaBirraFragment extends Fragment {

    private FragmentPreparaBirraBinding fragmentPreparaBirraBinding;
    private RicetteViewModel ricetteViewModel;
    private BirraViewModel birraViewModel;
    private AttrezziViewModel attrezziViewModel;
    List<IngredienteRicetta> listaIngredientiBirra;
    List<Ingrediente> listaIngredientiDisponibili;
    List<Attrezzo> listaAttrezziDisponibili;
    List<Integer> listaDifferenzaIngredienti;
    List<Attrezzo> listaAttrezziUtilizzati;
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
        ricetteViewModel = new ViewModelProvider(this,
                new RicetteViewModelFactory(getContext())).get(RicetteViewModel.class);
        birraViewModel = new ViewModelProvider(this,
                new BirraViewModelFactory(getContext())).get(BirraViewModel.class);

        attrezziViewModel = new ViewModelProvider(this,
                new AttrezziViewModelFactory(getContext())).get(AttrezziViewModel.class);

        listaIngredientiBirra = new ArrayList<>();
        listaIngredientiDisponibili = new ArrayList<>();
        listaAttrezziDisponibili = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPreparaBirraBinding = FragmentPreparaBirraBinding.inflate(inflater, container,false);
        return fragmentPreparaBirraBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Ricetta ricetta = PreparaBirraFragmentArgs.fromBundle(getArguments()).getRicetta();
        int litriBirraScelti = PreparaBirraFragmentArgs.fromBundle(getArguments()).getNumeroLitriBirraScelti();
        possiedeAttrezzi = false;


        ricetteViewModel.getDifferenzaIngredienti(ricetta.getId(), litriBirraScelti);
        fragmentPreparaBirraBinding.textViewNomePreparaBirra.setText(ricetta.getNome());


        ricetteViewModel.getIngredientiRicettaPerLitriRisultato().observe(getViewLifecycleOwner(), risultato ->  {
            if (risultato.isSuccessful()){
                listaIngredientiBirra = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();

                attrezziViewModel.readAllAttrezzi();


            } else {
                Snackbar.make(view, "non riesco a recuperare gli ingredienti", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        /*ingredienteViewModel.getReadAllIngredientiResult().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                listaIngredientiDisponibili = ((Risultato.IngredientiSuccesso) risultato).getData();

                listaDifferenzaIngredienti = new ArrayList<>();
                calcolaDifferenzaIngredienti();

                adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(getContext(), R.layout.lista_ingredienti_birra, listaIngredientiBirra, listaDifferenzaIngredienti);
                fragmentPreparaBirraBinding.listViewIngredrientiPreparaBirra.setAdapter(adapterListViewIngredientiBirra);
                fragmentPreparaBirraBinding.listViewIngredrientiPreparaBirra.setDivider(null);
            } else{
                //errore
            }
        });*/

        ricetteViewModel.getDifferenzaIngredientiRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()) {
                listaDifferenzaIngredienti = ((Risultato.ListaDifferenzaIngredientiSuccesso) risultato).getListaDifferenzaIngredienti();
                adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(getContext(), R.layout.lista_ingredienti_birra, listaIngredientiBirra, listaDifferenzaIngredienti);
                fragmentPreparaBirraBinding.listViewIngredrientiPreparaBirra.setAdapter(adapterListViewIngredientiBirra);
                fragmentPreparaBirraBinding.listViewIngredrientiPreparaBirra.setDivider(null);
            }
        });


        attrezziViewModel.getAllAttrezziResult().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                listaAttrezziDisponibili = ((Risultato.AttrezziSuccesso) risultato).getAttrezzi();
                listaAttrezziUtilizzati = new ArrayList<>();
                listaAttrezziUtilizzati = Ottimizzazione.ottimizzaAttrezzi(listaAttrezziDisponibili, litriBirraScelti);

                verificaAttrezziBirra();
            } else{
                //errore
            }
        });

        fragmentPreparaBirraBinding.buttonRicettaPreparaBirra.setOnClickListener(v -> {

            if (verificaIngredienti()){
                if (possiedeAttrezzi){
                    birraViewModel.createBirra(new Birra(litriBirraScelti, ricetta.getId()));

                    for (int i = 0; i < listaIngredientiDisponibili.size(); i++) {
                        listaIngredientiDisponibili.get(i).setQuantitaPosseduta(listaDifferenzaIngredienti.get(i));
                    }
                    //ingredienteViewModel.updateIngredienti(listaIngredientiDisponibili);

                    clearBackStack(getParentFragmentManager());
                    Snackbar.make(view, "Hai creato una nuova Birra!!", BaseTransientBottomBar.LENGTH_SHORT).show();

                } else {
                    Snackbar.make(view, "Attenzione ti mancano degli attrezzi", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(view, "Attenzione ti mancano degli ingredienti", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

    }

    private void clearBackStack(FragmentManager fragmentManager) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
/*
    public static double round(double n, int decimals) {
        return Math.floor(n * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    public void calcolaDifferenzaIngredienti(){
        possiedeIngredienti = true;
        for(int i=0; i < listaIngredientiBirra.size(); i++){
            int differenza =  listaIngredientiDisponibili.get(i).getQuantitaPosseduta() - ((int) Math.round(listaIngredientiBirra.get(i).getDosaggioIngrediente()));
            if (differenza < 0){
                possiedeIngredienti = false;
            }
            listaDifferenzaIngredienti.add(differenza);
        }
    }

    public void setDosaggioDaIngredienteRicetta(int litriBirraScelti){
        for (IngredienteRicetta ingredienteRicetta : listaIngredientiBirra) {
            if (ingredienteRicetta.getTipoIngrediente().equals(TipoIngrediente.ACQUA)) {
                ingredienteRicetta.setDosaggioIngrediente(round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti, 1));
            } else {
                ingredienteRicetta.setDosaggioIngrediente(Math.round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti));
            }
        }
    }

 */

    public void verificaAttrezziBirra(){
        if (listaAttrezziUtilizzati.size() == 3){
            possiedeAttrezzi = true;
        }
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
