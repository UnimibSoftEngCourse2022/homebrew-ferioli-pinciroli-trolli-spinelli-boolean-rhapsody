package it.unimib.brewday.ui.gestisci_ricette;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ricetta;

public class RicetteRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Ricetta> listaRicette;

    public final Context context;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onElementoRicettaClick(Ricetta ricetta);

        void onAggiungiRicettaClick(Ricetta ricetta);
    }

    public RicetteRecyclerViewAdapter(List<Ricetta> listaRicette, Context context,OnItemClickListener onItemClickListener) {
        this.listaRicette = listaRicette;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista_ricetta, parent, false);
        return new RicettaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RicettaViewHolder) holder).bind(listaRicette.get(position));

    }

    @Override
    public int getItemCount() {
        if (listaRicette != null){
            return listaRicette.size();
        }
        return 0;
    }

    TextView nomeRicetta;
    ImageButton aggiungiRicetta;

    public class RicettaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public RicettaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeRicetta = itemView.findViewById(R.id.textView_nomeRicetta);
            aggiungiRicetta = itemView.findViewById(R.id.imageButton_aggiungiRicetta);

            itemView.setOnClickListener(this);
            aggiungiRicetta.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.imageButton_aggiungiRicetta){
                //Inizio Birra
                onItemClickListener.onAggiungiRicettaClick(listaRicette.get(getBindingAdapterPosition()));
            } else {
                //Ricetta dettagliata
                onItemClickListener.onElementoRicettaClick(listaRicette.get(getBindingAdapterPosition()));
            }

        }

        public void bind(Ricetta ricetta){

            nomeRicetta.setText(ricetta.getNome());

        }
    }
}
