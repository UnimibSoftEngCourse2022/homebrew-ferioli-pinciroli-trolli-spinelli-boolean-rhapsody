package it.unimib.brewday.ui.gestione_birra;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;

public class BirraDettagliataFragment extends Fragment {


    public BirraDettagliataFragment() {
        // Required empty public constructor
    }

    public static BirraDettagliataFragment newInstance() {
        return new BirraDettagliataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_birra_dettagliata, container, false);
    }
}