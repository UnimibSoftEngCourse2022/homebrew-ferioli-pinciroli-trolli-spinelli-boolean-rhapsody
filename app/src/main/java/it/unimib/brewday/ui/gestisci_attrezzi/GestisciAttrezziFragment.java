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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoAttrezzo;

public class GestisciAttrezziFragment extends Fragment {

    private GestisciAttrezziViewModel mViewModel;
    private GestisciAttrezziAdapter attrezziAdapter;
    private RecyclerView recyclerView;

    public GestisciAttrezziFragment() {
        super(R.layout.fragment_gestisci_attrezzi);
    }

    public static GestisciAttrezziFragment newInstance() {
        return new GestisciAttrezziFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this,
                new GestisciAttrezziViewModelFactory(getContext()))
                .get(GestisciAttrezziViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gestisci_attrezzi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton addAttrezzo = view.findViewById(R.id.gestisciAttrezziFragment_imageButton_add);

        //Componenti card
        MaterialCardView materialCardView = view.findViewById(R.id.gestisciAttrezziFragment_materialCardView);
        EditText nomeAttrezzo = view.findViewById(R.id.fragmentGestisciAttrezzi_inserisciNome);
        EditText capacitaAttrezzo = view.findViewById(R.id.fragmentGestisciAttrezzi_inserisciCapacita);
        Spinner spinner = view.findViewById(R.id.fragmentGestisciAttrezzi_spinner);
        Button confermaInserimento = view.findViewById(R.id.fragmentGestisciAttrezzi_conferma);

        //Gestione spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getContext(),
                R.array.attrezzi,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Gestione visibilità card registrazione attrezzo
        mViewModel.isAddCardVisible.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                materialCardView.setVisibility(View.VISIBLE);

            else
                materialCardView.setVisibility(View.GONE);
        });

        addAttrezzo.setOnClickListener(v -> mViewModel.isAddCardVisible.setValue(!mViewModel.isAddCardVisible.getValue()));

        //Gestione conferma inserimento dati per creazione attrezzo
        confermaInserimento.setOnClickListener(v -> {

            if(nomeAttrezzo.getText().toString().equals("") ||
                    capacitaAttrezzo.getText().toString().equals("")) {
                Snackbar
                        .make(view,"Dati inseriti inaccettabili", BaseTransientBottomBar.LENGTH_SHORT)
                        .show();
            }
            else{
                String nome = nomeAttrezzo.getText().toString();
                double capacita = Integer.parseInt(capacitaAttrezzo.getText().toString());
                String tipo = spinner.getSelectedItem().toString();
                Attrezzo attrezzo = new Attrezzo(nome, TipoAttrezzo.valueOf(tipo.toUpperCase()), capacita);

                mViewModel.createAttrezzo(attrezzo);
                mViewModel.isAddCardVisible.setValue(!mViewModel.isAddCardVisible.getValue());
                nomeAttrezzo.setText(null);
                capacitaAttrezzo.setText(null);
                spinner.setAdapter(adapter);
            }
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
        });

        //Gestione risultato operazione cancellazione
        mViewModel.getDeleteAttrezzoResult().observe(this.getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()) {
                mViewModel.readAllAttrezzi();
            }
        });

        //Gestione risultato operazione aggiornamento
        mViewModel.getUpdateAttrezzoResult().observe(this.getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()) {
                mViewModel.readAllAttrezzi();
            }
        });
    }

    public void addNuoviAttrezzi(Risultato risultato) {
        if (risultato.isSuccessful() && risultato instanceof Risultato.AttrezziSuccesso) {
            List<Attrezzo> nuoviAttrezzi = ((Risultato.AttrezziSuccesso) risultato).getAttrezzi();
            if (attrezziAdapter == null) {
                attrezziAdapter = new GestisciAttrezziAdapter(nuoviAttrezzi, mViewModel);
                recyclerView.setAdapter(attrezziAdapter);
            } else {
                attrezziAdapter.setDataList(nuoviAttrezzi);
            }
        }
    }

}