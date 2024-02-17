package it.unimib.brewday.ui.visualizza_birre;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import it.unimib.brewday.databinding.FragmentListaBirreBinding;
import it.unimib.brewday.model.BirraConRicetta;
import it.unimib.brewday.model.Risultato;


public class ListaBirreFragment extends Fragment {

    private List<BirraConRicetta> listaBirre;
    private VisualizzaBirreViewModel visualizzaBirraViewModel;
    FragmentListaBirreBinding fragmentListaBirreBinding;

    public ListaBirreFragment() {
        //costruttore vuoto
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visualizzaBirraViewModel = new ViewModelProvider(this,
                new VisualizzaBirreViewModelFactory(getContext()))
                .get(VisualizzaBirreViewModel.class);
        listaBirre = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentListaBirreBinding = FragmentListaBirreBinding.inflate(inflater, container, false);
        return fragmentListaBirreBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewBirre = fragmentListaBirreBinding.recyclerViewListaBirre;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        AdapterRecyclerViewBirre adapterRecyclerViewBirre = new AdapterRecyclerViewBirre(listaBirre, new AdapterRecyclerViewBirre.ItemClickCallback() {
            @Override
            public void onElementoBirraClick(BirraConRicetta birra) {
                ListaBirreFragmentDirections.ActionBirreFragmentToBirraDettagliataFragment action =
                        ListaBirreFragmentDirections.actionBirreFragmentToBirraDettagliataFragment(birra);

                Navigation.findNavController(view).navigate(action);
            }

            @Override
            public void onTerminaBirraClick(BirraConRicetta birra) {
                birra.setTerminata(true);
                birra.setDataTerminazione(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                        .format(Calendar.getInstance().getTime()));
                visualizzaBirraViewModel.terminaBirra(birra);
            }
        }, visualizzaBirraViewModel);

        visualizzaBirraViewModel.getAllBirreResult().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                listaBirre.clear();
                listaBirre.addAll(((Risultato.AllBirreSuccesso) risultato).getAllBirre());
                adapterRecyclerViewBirre.notifyDataSetChanged();
            }
            else{
                Snackbar.make(view, "Perdindirindina", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        visualizzaBirraViewModel.getTerminaBirraRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                adapterRecyclerViewBirre.notifyDataSetChanged();
            }
            else{
                Snackbar.make(view, "NON VA", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        visualizzaBirraViewModel.getAllBirre();


        recyclerViewBirre.setLayoutManager(layoutManager);
        recyclerViewBirre.setAdapter(adapterRecyclerViewBirre);
    }
}