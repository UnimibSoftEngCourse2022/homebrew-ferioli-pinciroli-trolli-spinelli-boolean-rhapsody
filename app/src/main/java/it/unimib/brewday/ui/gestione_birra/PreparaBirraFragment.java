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
import it.unimib.brewday.util.Ottimizzazione;


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
         * Ottengo i dati dai safe args ed inizializzo alcuni elementi della schemrata (nome)
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

        /*
         * Osservo la lista degli attrezzi che ci sono a disposizione.
         */
        birraViewModel.getAllAttrezziNonInUsoResult().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                listaAttrezziDisponibili = ((Risultato.ListaAttrezziSuccesso) risultato).getAttrezzi();
                listaAttrezziSelezionati = new ArrayList<>();

                Risultato risultatoOttimizzazione =
                        Ottimizzazione.ottimizzaAttrezzi(listaAttrezziDisponibili, litriBirraScelti);

                if(risultatoOttimizzazione.isSuccessful()){
                    possiedeAttrezzi = true;
                    listaAttrezziSelezionati =
                            ((Risultato.ListaAttrezziSuccesso) risultatoOttimizzazione).getAttrezzi();

                }
                else{
                    if(risultatoOttimizzazione instanceof Risultato.ErroreConSuggerimentoLitri){
                        int litri = ((Risultato.ErroreConSuggerimentoLitri) risultatoOttimizzazione)
                                .getLitriSuggeriti();
                    }
                    else{

                    }
                }

                //TODO: integrare la nuova ottimizzazione degli attrezzi
                //listaAttrezziUtilizzati = Ottimizzazione.ottimizzaAttrezzi(listaAttrezziDisponibili, litriBirraScelti);

            } else{
                //errore
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

        birraViewModel.getDifferenzaIngredienti(ricetta.getId(), litriBirraScelti);
        birraViewModel.readAttrezziNonInUso();

        fragmentPreparaBirraBinding.buttonRicettaPreparaBirra.setOnClickListener(v -> {

            if (verificaIngredienti()){
                if (possiedeAttrezzi){
                    birraViewModel.createBirra(new Birra(litriBirraScelti, ricetta.getId()));

                    for (int i = 0; i < listaIngredientiDisponibili.size(); i++) {
                        listaIngredientiDisponibili.get(i).setQuantitaPosseduta(listaDifferenzaIngredienti.get(i));
                    }
                    //ingredienteViewModel.updateIngredienti(listaIngredientiDisponibili);

                    //clearBackStack(getParentFragmentManager());
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

    public boolean verificaIngredienti(){
        for (int i = 0; i < listaDifferenzaIngredienti.size(); i++) {
            if (listaDifferenzaIngredienti.get(i) < 0){
                return false;
            }
        }
        return  true;
    }

}
