package it.unimib.brewday.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;

public class AttrezziFragment extends Fragment {




    public AttrezziFragment() {
        // Required empty public constructor
    }


    public static AttrezziFragment newInstance() {

        return new AttrezziFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attrezzi, container, false);
    }
}