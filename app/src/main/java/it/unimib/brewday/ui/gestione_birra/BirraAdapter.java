package it.unimib.brewday.ui.gestione_birra;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.BirraConRicetta;

public class BirraAdapter extends RecyclerView.Adapter<BirraAdapter.ViewHolder>{

    private final List<BirraConRicetta> birre;
    private final itemClickCallback callback;

    public BirraAdapter(List<BirraConRicetta> birre, itemClickCallback callback) {
        this.birre = birre;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //da implementare
    }

    @Override
    public int getItemCount() {
        return 0;
    }





    //Classe che gestisce l'associazione tra i dati e la UI di un singolo elemneto della lista
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView nomeBirra;
        private final TextView numeroLitri;
        private final Button terminaProduzione;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeBirra = itemView.findViewById(R.id.textView_nomeBirra);
            numeroLitri = itemView.findViewById(R.id.textView_numeroLitri);
            terminaProduzione = itemView.findViewById(R.id.button_terminaProduzione);

            itemView.setOnClickListener(view -> {
                //listener in risposta al click sull'intera cella
            });
            terminaProduzione.setOnClickListener(view -> {
                //listener in risposta al click sul bottone termina della cella
            });
        }

        public void Bind(BirraConRicetta birra){
            //scrivere il binding
        }

    }


    //Callback da invocare per notificare l'evento di click relativo ad un preciso elemento della lista
    public interface itemClickCallback {
        void onElementoBirraClick(BirraConRicetta birra);
        void onTerminaBirraClick(BirraConRicetta birra);
    }
}
