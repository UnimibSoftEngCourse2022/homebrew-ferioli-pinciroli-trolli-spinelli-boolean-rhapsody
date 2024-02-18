package it.unimib.brewday.ui.cosa_prepariamo_oggi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import it.unimib.brewday.R;
import it.unimib.brewday.databinding.FragmentCosaPrepariamoOggiBinding;
import it.unimib.brewday.domain.StrategiaOttimizzazioneIngredienti;
import it.unimib.brewday.domain.StrategiaOttimizzazioneLitri;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;

public class CosaPrepariamoOggiFragment extends Fragment {

    private FragmentCosaPrepariamoOggiBinding fragmentCosaPrepariamoOggiBinding;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCosaPrepariamoOggiBinding = FragmentCosaPrepariamoOggiBinding.inflate(inflater, container, false);
        return fragmentCosaPrepariamoOggiBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton bottoneLitri = fragmentCosaPrepariamoOggiBinding.cosaPreparareOggiOttimizzaLitri;
        ImageButton bottoneIngredienti = fragmentCosaPrepariamoOggiBinding.cosaPreparareOggiOttimizzaIngredienti;

        cosaPrepariamoOggiViewModel.getCosaPrepariamoOggiRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){

                ricettaSelezionata = ((Risultato.OttimizzazioneSuccesso) risultato).getRicetta();
                litriRicettaSelezionata = ((Risultato.OttimizzazioneSuccesso) risultato).getLitri();

                if(ricettaSelezionata != null){
                    CosaPrepariamoOggiFragmentDirections.ActionCosaDevoPreparareOggiFragmentToPreparaBirraFragment action =
                            CosaPrepariamoOggiFragmentDirections.actionCosaDevoPreparareOggiFragmentToPreparaBirraFragment(ricettaSelezionata, litriRicettaSelezionata);
                    Navigation.findNavController(view).navigate(action);
                    cosaPrepariamoOggiViewModel.pulisciDati();
                }
                else{
                    Snackbar.make(view, R.string.nessuna_ricetta_disponibile, BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
            else{
                //TODO: implementare la corretta gestione dell'errore
                Snackbar.make(view, "ERRORE: " + ((Risultato.Errore) risultato).getMessaggio(), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        bottoneLitri.setOnClickListener(v -> cosaPrepariamoOggiViewModel.cosaPrepariamoOggi(new StrategiaOttimizzazioneLitri()));

        bottoneIngredienti.setOnClickListener(v -> cosaPrepariamoOggiViewModel.cosaPrepariamoOggi(new StrategiaOttimizzazioneIngredienti()));

    }
}