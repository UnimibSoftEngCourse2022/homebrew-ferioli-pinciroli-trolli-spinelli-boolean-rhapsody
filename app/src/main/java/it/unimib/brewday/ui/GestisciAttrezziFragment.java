package it.unimib.brewday.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.card.MaterialCardView;

import it.unimib.brewday.R;

public class GestisciAttrezziFragment extends Fragment {

    private GestisciAttrezziViewModel mViewModel;

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

        MaterialCardView materialCardView = view.findViewById(R.id.gestisciAttrezziFragment_materialCardView);
        ImageButton addAttrezzo = view.findViewById(R.id.gestisciAttrezziFragment_imageButton_add);
        Spinner spinner = view.findViewById(R.id.fragmentGestisciAttrezzi_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getContext(),
                R.array.planets_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        mViewModel.isAddCardVisible.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                materialCardView.setVisibility(View.VISIBLE);
            else
                materialCardView.setVisibility(View.INVISIBLE);
        });

        addAttrezzo.setOnClickListener(v -> {
            mViewModel.isAddCardVisible.setValue(!mViewModel.isAddCardVisible.getValue());
        });
    }

}