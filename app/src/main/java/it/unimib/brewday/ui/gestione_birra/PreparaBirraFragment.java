package it.unimib.brewday.ui.gestione_birra;

import static it.unimib.brewday.ui.Topbar.gestisciTopbar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentPreparaBirraBinding;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoIngrediente;

public class PreparaBirraFragment extends Fragment {

    private FragmentPreparaBirraBinding fragmentPreparaBirraBinding;
    private BirraViewModel birraViewModel;
    private List<IngredienteRicetta> listaIngredientiBirra;
    private List<Integer> listaDifferenzaIngredienti;
    private List<Attrezzo> listaAttrezziSelezionati;
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

        //Gestione Topbar
        gestisciTopbar((AppCompatActivity) requireActivity());

        /*
         * Ottengo i dati dai safe args ed inizializzo alcuni elementi della schermata (nome)
         */
        Ricetta ricetta = PreparaBirraFragmentArgs.fromBundle(getArguments()).getRicetta();
        int litriBirraScelti = PreparaBirraFragmentArgs.fromBundle(getArguments()).getNumeroLitriBirraScelti();
        fragmentPreparaBirraBinding.textViewNumeroLitriPreparaBirra.setText(Integer.toString(litriBirraScelti));
        possiedeAttrezzi = false;
        fragmentPreparaBirraBinding.textViewNomePreparaBirra.setText(ricetta.getNome());

        /*
         * Osservo il risultato del recupero degli ingredienti della ricetta moltiplicati per il numero di litri
         */
        birraViewModel.getDosaggiRisultato().observe(getViewLifecycleOwner(), risultato ->  {
            if (risultato.isSuccessful()){
                listaIngredientiBirra = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();
                birraViewModel.calcolaConsumoIngredienti(listaIngredientiBirra);
            } else {
                Snackbar.make(view, "Errore nel recupero e calcolo degli ingredienti", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        /*
         * Osservo il risultato della differenza tra gli ingredienti che ci sono a disposizione e
         * quelli richiesti per preparare i litri di birra specificati. (Serve per far comparire
         * a schermo gli errori)
         */
        birraViewModel.getConsumoIngredientiRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()) {
                listaDifferenzaIngredienti = ((Risultato.ListaDifferenzaIngredientiSuccesso) risultato).getListaDifferenzaIngredienti();

                adapterListViewIngredientiBirra = new AdapterListViewIngredientiBirra(
                        requireContext(),
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

        birraViewModel.getAttrezziSelezionatiRisultato().observe(getViewLifecycleOwner(), risultato ->
            checkAttrezziDisponibili(risultato, view)
        );


        birraViewModel.calcolaDosaggi(ricetta.getId(), litriBirraScelti);
        birraViewModel.getAndOptimizeAttrezziLiberi(litriBirraScelti);

        fragmentPreparaBirraBinding.buttonRicettaPreparaBirra.setOnClickListener(v ->
            checkPreparaBirra(new Birra(litriBirraScelti, ricetta.getId()), view)
        );

        /*
         * Gestione della lista della spesa
         */
        ImageButton listaSpesaButton = fragmentPreparaBirraBinding.imagebuttonGeneraListaSpesa;
        listaSpesaButton.setOnClickListener(v -> {
            StringBuilder testoDaSalvare = new StringBuilder("Lista della spesa:\n\n");
            for (IngredienteRicetta ingredienteBirra : listaIngredientiBirra) {
                String unitaMisura = " g";
                if(ingredienteBirra.getTipoIngrediente() == TipoIngrediente.ACQUA) {
                    unitaMisura = " L";
                }
                testoDaSalvare.append("- ")
                        .append(ingredienteBirra.getTipoIngrediente())
                        .append(" in quantità ")
                        .append(ingredienteBirra.getDosaggioIngrediente())
                        .append(unitaMisura).append("\n\n");
            }

            Intent intent = new Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, (Serializable) testoDaSalvare);
            startActivity(intent);
        });
    }


    private boolean verificaIngredienti(){
        for (int i = 0; i < listaDifferenzaIngredienti.size(); i++) {
            if (listaDifferenzaIngredienti.get(i) < 0){
                return false;
            }
        }
        return  true;
    }

    private void checkPreparaBirra(Birra birra, View view){
        if (verificaIngredienti()){
            if (possiedeAttrezzi){
                birraViewModel.createBirra(birra, listaDifferenzaIngredienti, listaAttrezziSelezionati);
            }
            else {
                Snackbar.make(view, "Gli attrezzi sono troppo piccoli", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }
        else {
            Snackbar.make(view, "Attenzione ti mancano degli ingredienti", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    private void checkAttrezziDisponibili(Risultato risultato, View view){
        if(risultato.isSuccessful()){
            possiedeAttrezzi = true;
            fragmentPreparaBirraBinding.imageViewControloAttrezziPreparaBirra.setImageResource(R.drawable.check);
            fragmentPreparaBirraBinding.textViewLitriSuggeriti.setVisibility(View.GONE);
            fragmentPreparaBirraBinding.imageViewLitriMassimi.setVisibility(View.GONE);
            listaAttrezziSelezionati = ((Risultato.ListaAttrezziSuccesso) risultato).getAttrezzi();
        }
        else{
            fragmentPreparaBirraBinding.imageViewControloAttrezziPreparaBirra.setImageResource(R.drawable.fail);
            if(risultato instanceof Risultato.ErroreConSuggerimentoLitri){
                int litriSuggeriti = ((Risultato.ErroreConSuggerimentoLitri) risultato).getLitriSuggeriti();
                fragmentPreparaBirraBinding.textViewLitriSuggeriti.setText(litriSuggeriti + "L");
                fragmentPreparaBirraBinding.textViewLitriSuggeriti.setVisibility(View.VISIBLE);
                fragmentPreparaBirraBinding.imageViewLitriMassimi.setVisibility(View.VISIBLE);
            }
            else{
                Snackbar.make(view, "Attrezzi mancanti", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }
    }

}
