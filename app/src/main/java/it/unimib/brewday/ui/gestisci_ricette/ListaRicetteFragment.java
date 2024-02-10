package it.unimib.brewday.ui.gestisci_ricette;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ricetta;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListaRicetteFragment extends Fragment {

    List<Ricetta> listaRicette;

    RicetteRecyclerViewAdapter ricetteRecyclerViewAdapter;

    RecyclerView recyclerViewRicette;
    public ListaRicetteFragment() {
        // Required empty public constructor
    }

    public static ListaRicetteFragment newInstance() {

        return new ListaRicetteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
         recyclerViewRicette = view.findViewById(R.id.recyclerview_listaRicette);
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewRicette);
        recyclerViewRicette.setLayoutManager(layoutManager);
        recyclerViewRicette.setAdapter(ricetteRecyclerViewAdapter);

        creaRicettaButton.setOnClickListener(v ->
            Navigation.findNavController(requireView()).navigate(R.id.action_listaRicetteFragment_to_creaRicettaFragment));


    }
    String ricettaRimossaMessaggio ;
    Ricetta ricettaRimossa;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int posizione = viewHolder.getBindingAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    ricettaRimossa = listaRicette.get(posizione);
                    ricettaRimossaMessaggio = "rimossa ricetta "+listaRicette.get(posizione).getNome();
                    listaRicette.remove(posizione);

                   ricetteRecyclerViewAdapter.notifyItemRemoved(posizione);
                    Snackbar.make(recyclerViewRicette, ricettaRimossaMessaggio, Snackbar.LENGTH_LONG).setAction("Annulla", v -> {

                        listaRicette.add(posizione, ricettaRimossa);
                       // ricetteRecyclerViewAdapter.notifyItemInserted(posizione);
                        recyclerViewRicette.setAdapter(ricetteRecyclerViewAdapter);
                    }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                    .addSwipeLeftBackgroundColor(R.color.md_theme_light_error)
                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_outline_24)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.md_theme_light_error))
                    .addActionIcon(R.drawable.baseline_delete_outline_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }


    };
}