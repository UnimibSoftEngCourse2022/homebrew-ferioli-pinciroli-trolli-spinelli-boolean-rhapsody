package it.unimib.brewday;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;


public class BirreFragment extends Fragment {


    public BirreFragment() {
        // Required empty public constructor
    }


    public static BirreFragment newInstance() {
        return new BirreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_birre, container, false);
    }
}