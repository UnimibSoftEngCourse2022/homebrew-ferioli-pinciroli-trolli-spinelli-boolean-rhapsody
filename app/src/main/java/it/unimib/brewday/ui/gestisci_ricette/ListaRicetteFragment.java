package it.unimib.brewday.ui.gestisci_ricette;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.ui.gestione_birra.InserisciLitriDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListaRicetteFragment extends Fragment {

    List<Ricetta> listaRicette;
    RicetteViewModel ricettaViewModel;
    RicetteRecyclerViewAdapter ricetteRecyclerViewAdapter;
    RecyclerView recyclerViewRicette;
    String ricettaRimossaMessaggio ;
    Ricetta ricettaRimossa;

    public ListaRicetteFragment() {
        // Required empty public constructor
    }

    public static ListaRicetteFragment newInstance() {

        return new ListaRicetteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ricettaViewModel = new ViewModelProvider(this,
                new RicetteViewModelFactory(getContext()))
                .get(RicetteViewModel.class);
        listaRicette = new ArrayList<>();
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

        ricettaViewModel.getRicetteRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                this.listaRicette.clear();
                this.listaRicette.addAll(((Risultato.ListaRicetteSuccesso) risultato).getRicette());

                ricetteRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        ricettaViewModel.getAllRicette();

        ricettaViewModel.getDeleteRicettaRisultato().observe(getViewLifecycleOwner(), risultato -> {
            if (risultato.isSuccessful()){
                ricetteRecyclerViewAdapter.notifyDataSetChanged();
            }
        });


        ricetteRecyclerViewAdapter = new RicetteRecyclerViewAdapter(listaRicette, getContext(),
                new RicetteRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onElementoRicettaClick(Ricetta ricetta) {
                        ListaRicetteFragmentDirections.ActionListaRicetteFragmentToRicettaDettagliataFragment action =
                                ListaRicetteFragmentDirections.actionListaRicetteFragmentToRicettaDettagliataFragment(ricetta);
                        Navigation.findNavController(view).navigate(action);
                    }

                    @Override
                    public void onAggiungiRicettaClick(Ricetta ricetta) {
                        InserisciLitriDialog litriDialog = new InserisciLitriDialog(ricetta, view, true );
                        litriDialog.show(getParentFragmentManager(), "Scegli litri birra da preparare");
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

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int posizione = viewHolder.getBindingAdapterPosition();

                if(direction == ItemTouchHelper.LEFT) {
                    ricettaRimossa = listaRicette.get(posizione);
                    ricettaRimossaMessaggio = "Rimossa la ricetta "+listaRicette.get(posizione).getNome();
                    listaRicette.remove(posizione);
                    ricettaViewModel.deleteRicetta(ricettaRimossa);
                    ricetteRecyclerViewAdapter.notifyItemRemoved(posizione);
                    Snackbar.make(recyclerViewRicette, ricettaRimossaMessaggio, BaseTransientBottomBar.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                    .addSwipeLeftBackgroundColor(R.color.md_theme_light_error)
                    .addSwipeLeftActionIcon(R.drawable.delete_24px)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.md_theme_light_error))
                    .addActionIcon(R.drawable.delete_24px)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }


    };
}