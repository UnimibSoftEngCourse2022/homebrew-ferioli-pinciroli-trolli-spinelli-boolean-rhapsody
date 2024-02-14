package it.unimib.brewday.ui.gestione_birra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.unimib.brewday.databinding.FragmentListaBirreBinding;
import it.unimib.brewday.model.BirraConRicetta;
import it.unimib.brewday.model.Risultato;


public class ListaBirreFragment extends Fragment {

    private List<BirraConRicetta> listaBirre;
    private BirraViewModel birraViewModel;
    FragmentListaBirreBinding fragmentListaBirreBinding;

    public ListaBirreFragment() {}

    public static ListaBirreFragment newInstance() {
        return new ListaBirreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        birraViewModel = new ViewModelProvider(this,
                new BirraViewModelFactory(getContext()))
                .get(BirraViewModel.class);
        listaBirre = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentListaBirreBinding = FragmentListaBirreBinding.inflate(inflater, container, false);
        return fragmentListaBirreBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewBirre = fragmentListaBirreBinding.recyclerViewListaBirre;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        ListaBirreAdapter listaBirreAdapter = new ListaBirreAdapter(listaBirre, new ListaBirreAdapter.itemClickCallback() {
            @Override
            public void onElementoBirraClick(BirraConRicetta birra) {
                // TODO implementare
            }

            @Override
            public void onTerminaBirraClick(BirraConRicetta birra) {
                birra.setTerminata(true);
                String dataTerminazione = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                        .format(Calendar.getInstance().getTime());
                birra.setDataTerminazione(dataTerminazione);
                birraViewModel.updateBirra(birra);
            }
        });

        birraViewModel.getAllBirreResult().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                listaBirre.clear();
                listaBirre.addAll(((Risultato.AllBirreSuccesso) risultato).getAllBirre());
                listaBirreAdapter.notifyDataSetChanged();
            }
            else{
                Snackbar.make(view, "Perdindirindina", Snackbar.LENGTH_LONG).show();
            }
        });

        birraViewModel.getUpdateBirraRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                listaBirreAdapter.notifyDataSetChanged();
            }
            else{
                Snackbar.make(view, "NON VA", Snackbar.LENGTH_LONG).show();
            }
        });

        birraViewModel.getAllBirre();


        recyclerViewBirre.setLayoutManager(layoutManager);
        recyclerViewBirre.setAdapter(listaBirreAdapter);
    }
}