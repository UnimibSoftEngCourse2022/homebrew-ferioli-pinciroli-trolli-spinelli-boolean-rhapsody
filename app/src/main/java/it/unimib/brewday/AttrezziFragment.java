package it.unimib.brewday;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttrezziFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttrezziFragment extends Fragment {




    public AttrezziFragment() {
        // Required empty public constructor
    }


    public static AttrezziFragment newInstance() {

        return new AttrezziFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attrezzi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}