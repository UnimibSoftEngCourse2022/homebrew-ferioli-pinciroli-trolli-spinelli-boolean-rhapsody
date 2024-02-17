package it.unimib.brewday.ui.cosa_preparare_oggi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;

public class CosaPreparareOggiFragment extends Fragment {

    private CosaPreparareOggiViewModel cosaPreparareOggiViewModel;
    private Ricetta ricettaSelezionata;
    private int litriRicettaSelezionata;

    public CosaPreparareOggiFragment() {
        // Required empty public constructor
    }


    public static CosaPreparareOggiFragment newInstance() {
        return new CosaPreparareOggiFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cosaPreparareOggiViewModel = new CosaPreparareOggiViewModelFactory(requireContext()).create(CosaPreparareOggiViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cosa_preparare_oggi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cosaPreparareOggiViewModel.getRicettaConsumoMassimoRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                //recuperare dosaggi e recuperare differenza
                ricettaSelezionata = ((Risultato.MassimizzazioneConsumoIngredientiSuccesso) risultato).getRicetta();
                litriRicettaSelezionata = ((Risultato.MassimizzazioneConsumoIngredientiSuccesso) risultato).getLitri();

                if(ricettaSelezionata != null){
                    CosaPreparareOggiFragmentDirections.ActionCosaDevoPreparareOggiFragmentToPreparaBirraFragment action =
                            CosaPreparareOggiFragmentDirections.actionCosaDevoPreparareOggiFragmentToPreparaBirraFragment(ricettaSelezionata, litriRicettaSelezionata);
                    Navigation.findNavController(view).navigate(action);
                }
                else{
                    //gestione errore
                }
            }
            else{
                Snackbar.make(view, "SI SMERDA: " + ((Risultato.Errore) risultato).getMessaggio(), Snackbar.LENGTH_LONG).show();
            }
        });

        cosaPreparareOggiViewModel.suggerisciRicettaConConsumoMassimo();
    }
}