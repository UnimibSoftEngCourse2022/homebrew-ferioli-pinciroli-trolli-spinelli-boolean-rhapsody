package it.unimib.brewday.ui.gestione_birra;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.unimib.brewday.R;
import it.unimib.brewday.ui.gestisci_ricette.RicetteViewModel;
import it.unimib.brewday.ui.gestisci_ricette.RicetteViewModelFactory;


public class BirreFragment extends Fragment {


    private BirraViewModel birraViewModel;

    public BirreFragment() {}

    public static BirreFragment newInstance() {
        return new BirreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        birraViewModel = new ViewModelProvider(this,
                new BirraViewModelFactory(getContext()))
                .get(BirraViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_birre, container, false);
    }
}