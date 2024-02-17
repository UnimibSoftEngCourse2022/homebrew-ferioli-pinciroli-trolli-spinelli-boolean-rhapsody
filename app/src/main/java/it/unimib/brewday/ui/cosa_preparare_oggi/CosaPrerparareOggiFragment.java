package it.unimib.brewday.ui.cosa_preparare_oggi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;

public class CosaPrerparareOggiFragment extends Fragment {


    public CosaPrerparareOggiFragment() {
        // Required empty public constructor
    }


    public static CosaPrerparareOggiFragment newInstance() {
        return new CosaPrerparareOggiFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cosa_preparare_oggi, container, false);
    }
}