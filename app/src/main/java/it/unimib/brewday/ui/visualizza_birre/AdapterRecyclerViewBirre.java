package it.unimib.brewday.ui.visualizza_birre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.BirraConRicetta;

public class AdapterRecyclerViewBirre extends RecyclerView.Adapter<AdapterRecyclerViewBirre.ViewHolder> {

    private final List<BirraConRicetta> listaBirre;
    private final ItemClickCallback callback;


    public AdapterRecyclerViewBirre(List<BirraConRicetta> listaBirre, ItemClickCallback callback) {
        this.listaBirre = listaBirre;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista_birre, parent, false);
        return new AdapterRecyclerViewBirre.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.bind(listaBirre.get(position));

    }


    @Override
    public int getItemCount() {
        if (listaBirre != null){
            return listaBirre.size();
        }
        return 0;
    }





    //Classe che gestisce l'associazione tra i dati e la UI di un singolo elemento della lista
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView nomeBirra;
        private final TextView numeroLitri;
        private final ImageButton terminaProduzione;
        private final TextView dataTerminazione;
        private final ImageView iconaTerminazione;
        private final CardView cardBirra;
        private final RatingBar ratingBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeBirra = itemView.findViewById(R.id.textView_nomeBirra);
            numeroLitri = itemView.findViewById(R.id.textView_numeroLitri);
            terminaProduzione = itemView.findViewById(R.id.imageButton_terminaProduzione);
            dataTerminazione = itemView.findViewById(R.id.textView_dataTerminazione);
            iconaTerminazione = itemView.findViewById(R.id.imageView_dataTerminazione);
            cardBirra = itemView.findViewById(R.id.cardView_birra);
            ratingBar = itemView.findViewById(R.id.ratingBar_mediaNoteDegustazione);

            itemView.setOnClickListener(view ->
                callback.onElementoBirraClick(listaBirre.get(getBindingAdapterPosition()))
            );
            terminaProduzione.setOnClickListener(view -> callback.onTerminaBirraClick(listaBirre.get(getBindingAdapterPosition())));
        }

        public void bind(BirraConRicetta birra){
            nomeBirra.setText(birra.getNomeRicetta());
            numeroLitri.setText(birra.getLitriProdotti() + "L");
            if(birra.isTerminata()){
                terminaProduzione.setVisibility(View.GONE);
                dataTerminazione.setText(birra.getDataTerminazione());
                dataTerminazione.setVisibility(View.VISIBLE);
                iconaTerminazione.setVisibility(View.VISIBLE);
                cardBirra.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.md_theme_light_secondaryContainer));
                ratingBar.setRating(birra.getMediaRecensione());
                ratingBar.setVisibility(View.VISIBLE);
            }
            else{
                ratingBar.setVisibility(View.GONE);
                terminaProduzione.setVisibility(View.VISIBLE);
                dataTerminazione.setVisibility(View.GONE);
                iconaTerminazione.setVisibility(View.GONE);
                cardBirra.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.md_theme_light_primaryContainer));
            }




        }


    }


    //Callback da invocare per notificare l'evento di click relativo ad un preciso elemento della lista
    public interface ItemClickCallback {
        void onElementoBirraClick(BirraConRicetta birra);
        void onTerminaBirraClick(BirraConRicetta birra);
    }
}
