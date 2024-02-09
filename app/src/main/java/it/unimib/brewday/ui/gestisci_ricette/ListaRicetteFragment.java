package it.unimib.brewday.ui.gestisci_ricette;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ricetta;

public class ListaRicetteFragment extends Fragment {

    List<Ricetta> listaRicette;

    RicetteRecyclerViewAdapter ricetteRecyclerViewAdapter;
    public ListaRicetteFragment() {
        // Required empty public constructor
    }

    public static ListaRicetteFragment newInstance() {

        return new ListaRicetteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaRicette = new ArrayList<>();
        listaRicette.add(new Ricetta(1, "guiness"));
        listaRicette.add(new Ricetta(2, "franzizkale"));
        listaRicette.add(new Ricetta(3, "moretti"));
        listaRicette.add(new Ricetta(4, "forste"));
        listaRicette.add(new Ricetta(5, "valDiFiemmeBeer"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_ricette, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewRicette = view.findViewById(R.id.recyclerview_listaRicette);
        FloatingActionButton creaRicettaButton = view.findViewById(R.id.button_toCreaRicetta);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);




         ricetteRecyclerViewAdapter = new RicetteRecyclerViewAdapter(listaRicette,
                getContext(), new RicetteRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onElementoRicettaClick(Ricetta ricetta) {

            }

            @Override
            public void onAggiungiRicettaClick(Ricetta ricetta) {

            }
        }
        );

        recyclerViewRicette.setLayoutManager(layoutManager);
        recyclerViewRicette.setAdapter(ricetteRecyclerViewAdapter);

        creaRicettaButton.setOnClickListener(v ->
            Navigation.findNavController(requireView()).navigate(R.id.action_listaRicetteFragment_to_creaRicettaFragment));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewRicette);
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int posizione = viewHolder.getAdapterPosition();
            switch (direction ) {
                case ItemTouchHelper.LEFT:
                    listaRicette.remove(posizione);
                    ricetteRecyclerViewAdapter.notifyItemRemoved(posizione);


                    break;
            }
        }
    };
}