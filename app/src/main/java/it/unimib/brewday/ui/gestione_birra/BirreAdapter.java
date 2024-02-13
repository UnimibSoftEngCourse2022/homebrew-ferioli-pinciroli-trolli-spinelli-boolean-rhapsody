package it.unimib.brewday.ui.gestione_birra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.BirraConRicetta;

public class BirreAdapter extends RecyclerView.Adapter<BirreAdapter.ViewHolder>{

    private final List<BirraConRicetta> listaBirre;
    private final itemClickCallback callback;

    public BirreAdapter(List<BirraConRicetta> listaBirre, itemClickCallback callback) {
        this.listaBirre = listaBirre;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista_birre, parent, false);
        return new BirreAdapter.ViewHolder(view);
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
        private final Button terminaProduzione;
        private final CardView cardBirra;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeBirra = itemView.findViewById(R.id.textView_nomeBirra);
            numeroLitri = itemView.findViewById(R.id.textView_numeroLitri);
            terminaProduzione = itemView.findViewById(R.id.button_terminaProduzione);
            cardBirra = itemView.findViewById(R.id.cardView_birra);

            itemView.setOnClickListener(view -> {
                //listener in risposta al click sull'intera cella
            });
            terminaProduzione.setOnClickListener(view -> {
                callback.onTerminaBirraClick(listaBirre.get(getBindingAdapterPosition()));
            });
        }

        public void bind(BirraConRicetta birra){
            nomeBirra.setText(birra.getNomeRicetta());
            numeroLitri.setText(birra.getLitriProdotti() + "L");
            if(birra.isTerminata()){
                terminaProduzione.setVisibility(View.GONE);
                cardBirra.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.md_theme_light_secondaryContainer));
            }
            else{
                terminaProduzione.setVisibility(View.VISIBLE);
                cardBirra.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.md_theme_light_primaryContainer));
            }
        }

    }


    //Callback da invocare per notificare l'evento di click relativo ad un preciso elemento della lista
    public interface itemClickCallback {
        void onElementoBirraClick(BirraConRicetta birra);
        void onTerminaBirraClick(BirraConRicetta birra);
    }
}
