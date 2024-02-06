package it.unimib.brewday.ui.gestisci_ricette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.brewday.R;

public class RicetteRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista_ricetta, parent, false);
        return new RicettaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    TextView nomeRicetta;
    ImageButton aggiungiRicetta;

    public class RicettaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public RicettaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeRicetta = itemView.findViewById(R.id.textView_nomeRicetta);
            aggiungiRicetta = itemView.findViewById(R.id.imageButton_aggiungiRicetta);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
