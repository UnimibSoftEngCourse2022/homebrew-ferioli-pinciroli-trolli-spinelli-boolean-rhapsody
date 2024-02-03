package it.unimib.brewday.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;

public class IngredientiFragment extends Fragment {


    public IngredientiFragment() {
        // Required empty public constructor
    }


    public static IngredientiFragment newInstance() {

        return new IngredientiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredienti, container, false);
    }
}