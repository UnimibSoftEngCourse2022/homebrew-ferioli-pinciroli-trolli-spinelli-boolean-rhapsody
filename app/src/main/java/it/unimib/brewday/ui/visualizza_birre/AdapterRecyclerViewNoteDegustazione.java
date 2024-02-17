package it.unimib.brewday.ui.visualizza_birre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.BirraConRicetta;
import it.unimib.brewday.model.NotaDegustazione;

public class AdapterRecyclerViewNoteDegustazione extends RecyclerView.Adapter<AdapterRecyclerViewNoteDegustazione.ViewHolder> {

    private final List<NotaDegustazione> listaNotaDegustazione;

    public AdapterRecyclerViewNoteDegustazione(List<NotaDegustazione> listaNotaDegustazione) {
        this.listaNotaDegustazione = listaNotaDegustazione;
    }

    @NonNull
    @Override
    public AdapterRecyclerViewNoteDegustazione.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista_note_degustazione, parent, false);
        return new AdapterRecyclerViewNoteDegustazione.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listaNotaDegustazione.get(position));
    }


    @Override
    public int getItemCount() {
        if(listaNotaDegustazione != null){
            return listaNotaDegustazione.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nomeRecensore;
        private final TextView commento;
        private final RatingBar recensione;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeRecensore = itemView.findViewById(R.id.textView_nomeNotaDettagliata_elementoLista);
            commento = itemView.findViewById(R.id.textView_commentoNotaDettagliata_elementoLista);
            recensione = itemView.findViewById(R.id.ratingBar_notaDettagliata_elementoLista);
        }

        public void bind(NotaDegustazione notaDegustazione) {

            nomeRecensore.setText(notaDegustazione.getRecensore());
            commento.setText(notaDegustazione.getCommento());
            recensione.setRating(notaDegustazione.getRecensione());

        }
    }
}