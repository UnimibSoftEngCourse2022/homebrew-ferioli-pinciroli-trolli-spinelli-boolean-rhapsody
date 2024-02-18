package it.unimib.brewday.ui.cosa_prepariamo_oggi;

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
import it.unimib.brewday.domain.StrategiaOttimizzazioneLitri;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;

public class CosaPrepariamoOggiFragment extends Fragment {

    private CosaPrepariamoOggiViewModel cosaPrepariamoOggiViewModel;
    private Ricetta ricettaSelezionata;
    private int litriRicettaSelezionata;

    public CosaPrepariamoOggiFragment() {
        // Required empty public constructor
    }


    public static CosaPrepariamoOggiFragment newInstance() {
        return new CosaPrepariamoOggiFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cosaPrepariamoOggiViewModel = new CosaPrepariamoOggiViewModelFactory(requireContext()).create(CosaPrepariamoOggiViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cosa_prepariamo_oggi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cosaPrepariamoOggiViewModel.getRicettaConsumoMassimoRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                //recuperare dosaggi e recuperare differenza
                ricettaSelezionata = ((Risultato.OttimizzazioneSuccesso) risultato).getRicetta();
                litriRicettaSelezionata = ((Risultato.OttimizzazioneSuccesso) risultato).getLitri();

                if(ricettaSelezionata != null){
                    CosaPrepariamoOggiFragmentDirections.ActionCosaDevoPreparareOggiFragmentToPreparaBirraFragment action =
                            CosaPrepariamoOggiFragmentDirections.actionCosaDevoPreparareOggiFragmentToPreparaBirraFragment(ricettaSelezionata, litriRicettaSelezionata);
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

        cosaPrepariamoOggiViewModel.cosaPrepariamoOggi(new StrategiaOttimizzazioneLitri());
    }
}