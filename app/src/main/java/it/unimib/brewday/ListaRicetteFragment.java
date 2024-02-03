package it.unimib.brewday;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaRicetteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaRicetteFragment extends Fragment {


    public ListaRicetteFragment() {
        // Required empty public constructor
    }

    public static ListaRicetteFragment newInstance() {

        return new ListaRicetteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_ricette, container, false);
    }
}