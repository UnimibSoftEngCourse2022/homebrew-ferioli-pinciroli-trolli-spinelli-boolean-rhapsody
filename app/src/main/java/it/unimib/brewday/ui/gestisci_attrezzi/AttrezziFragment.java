package it.unimib.brewday.ui.gestisci_attrezzi;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.util.RegistroErrori;

public class AttrezziFragment extends Fragment {

    private AttrezziViewModel mViewModel;
    private AdapterRecyclerViewAttrezzi adapterRecyclerViewAttrezzi;
    private RecyclerView recyclerView;

    public AttrezziFragment() {
        super(R.layout.fragment_gestisci_attrezzi);
    }

    public static AttrezziFragment newInstance() {
        return new AttrezziFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this,
                new AttrezziViewModelFactory(getContext()))
                .get(AttrezziViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gestisci_attrezzi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton addAttrezzo = view.findViewById(R.id.gestisciAttrezziFragment_imageButton_add);

        addAttrezzo.setOnClickListener(v -> {
            InserisciAttrezzoDialog dialog = new InserisciAttrezzoDialog(mViewModel);
            dialog.show(getParentFragmentManager(), "Inserisci nuovo attrezzo");
        });

        //Gestione stampa a schermo degli attrezzi registrati
        recyclerView = view.findViewById(R.id.fragmentGestisciAttrezzi_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mViewModel.readAllAttrezzi();

        //Gestione risultato operazione di lettura
        mViewModel.getAllAttrezziResult().observe(this.getViewLifecycleOwner(), this::addNuoviAttrezzi);

        //Gestione risultato operazione creazione
        mViewModel.getCreateAttrezzoResult().observe(this.getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()) {
                mViewModel.readAllAttrezzi();
            }
            else{
                String errore = ((Risultato.Errore) risultato).getMessaggio();
                Snackbar.make(view, getString(RegistroErrori.getInstance().getErrore(errore)), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        //Gestione risultato operazione cancellazione
        mViewModel.getDeleteAttrezzoResult().observe(this.getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()) {
                mViewModel.readAllAttrezzi();
            }
            else{
                String errore = ((Risultato.Errore) risultato).getMessaggio();
                Snackbar.make(view, getString(RegistroErrori.getInstance().getErrore(errore)), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        //Gestione risultato operazione aggiornamento
        mViewModel.getUpdateAttrezzoResult().observe(this.getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()) {
                mViewModel.readAllAttrezzi();
            }
            else{
                String errore = ((Risultato.Errore) risultato).getMessaggio();
                Snackbar.make(view, getString(RegistroErrori.getInstance().getErrore(errore)), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    public void addNuoviAttrezzi(Risultato risultato) {
        if (risultato.isSuccessful() && risultato instanceof Risultato.ListaAttrezziSuccesso) {
            List<Attrezzo> nuoviAttrezzi = ((Risultato.ListaAttrezziSuccesso) risultato).getAttrezzi();
            if (adapterRecyclerViewAttrezzi == null) {
                adapterRecyclerViewAttrezzi = new AdapterRecyclerViewAttrezzi(nuoviAttrezzi, mViewModel, false);
                recyclerView.setAdapter(adapterRecyclerViewAttrezzi);
            } else {
                adapterRecyclerViewAttrezzi.setDataList(nuoviAttrezzi);
            }
        }
        else{
            String errore = ((Risultato.Errore) risultato).getMessaggio();
            Snackbar.make(requireView(), getString(RegistroErrori.getInstance().getErrore(errore)), BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

}