package it.unimib.brewday.ui.gestione_birra;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;


public class BirreFragment extends Fragment {


    public BirreFragment() {
    }


    public static BirreFragment newInstance() {
        return new BirreFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_birre, container, false);
    }
}