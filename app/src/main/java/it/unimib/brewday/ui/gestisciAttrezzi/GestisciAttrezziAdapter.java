package it.unimib.brewday.ui.gestisciAttrezzi;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Attrezzo;

public class GestisciAttrezziAdapter extends RecyclerView.Adapter<GestisciAttrezziAdapter.ViewHolder>{

    private List<Attrezzo> listaAttrezzi;

    public GestisciAttrezziAdapter(List<Attrezzo> dataList) {
        this.listaAttrezzi = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nomeAttrezzo.setText(listaAttrezzi.get(position).nome);
        holder.capacitaAttrezzo.setText(String.valueOf(listaAttrezzi.get(position).capacita));
        holder.tipoAttrezzo.setText(listaAttrezzi.get(position).tipoAttrezzo.getNome());
    }

    @Override
    public int getItemCount() {
        return listaAttrezzi.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nomeAttrezzo;
        private final TextView capacitaAttrezzo;
        private final TextView tipoAttrezzo;

        public ViewHolder(View view) {
            super(view);

            nomeAttrezzo = view.findViewById(R.id.oneRowCard_nomeReale);
            capacitaAttrezzo = view.findViewById(R.id.oneRowCard_capacitaReale);
            tipoAttrezzo = view.findViewById(R.id.oneRowCard_tipoReale);

        }
    }

    public void setDataList(List<Attrezzo> newDataList) {
        this.listaAttrezzi = newDataList;
        notifyDataSetChanged();
    }
}
