package it.unimib.brewday.ui.gestisci_attrezzi;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.TipoAttrezzo;

public class AttrezziAdapter extends RecyclerView.Adapter<AttrezziAdapter.ViewHolder>{

    private List<Attrezzo> listaAttrezzi;
    private final AttrezziViewModel attrezziViewModel;

    public AttrezziAdapter(List<Attrezzo> dataList, AttrezziViewModel viewModel) {
        this.listaAttrezzi = dataList;
        attrezziViewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista_attrezzi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Gestione nome
        holder.nomeAttrezzo.setText(listaAttrezzi.get(position).getNome());
        holder.nomeAttrezzo.setEnabled(false);

        //Gestione capacità
        holder.capacitaAttrezzo.setText(String.valueOf(listaAttrezzi.get(position).getCapacita()));
        holder.capacitaAttrezzo.setEnabled(false);

        //Gestione spinner
        holder.tipoAttrezzo.setAdapter(holder.adapter);
        holder.tipoAttrezzo.setEnabled(false);
        String tipoAttrezzoStringFormat = listaAttrezzi.get(position).getTipoAttrezzo().getNome();
        holder.tipoAttrezzo.setSelection(holder.adapter.getPosition(tipoAttrezzoStringFormat));

        //Gestione bottone modifica
        holder.modifica.setOnClickListener(v -> {

            //Rendo editabili le varie edit text
            holder.nomeAttrezzo.setEnabled(!holder.nomeAttrezzo.isEnabled());
            holder.capacitaAttrezzo.setEnabled(!holder.capacitaAttrezzo.isEnabled());
            holder.tipoAttrezzo.setEnabled(!holder.tipoAttrezzo.isEnabled());

            //Controllo sulla tipologia di bottone
            if(holder.isModifica) {
                holder.setModifica(false);
                holder.modifica.setImageResource(R.drawable.cancel_24px);
                holder.conferma.setVisibility(View.VISIBLE);
            }
            else {
                holder.setModifica(true);
                holder.conferma.setVisibility(View.INVISIBLE);
                holder.modifica.setImageResource(R.drawable.edit_24px);
                holder.nomeAttrezzo.setText(listaAttrezzi.get(position).getNome());
                holder.capacitaAttrezzo.setText(String.valueOf(listaAttrezzi.get(position).getCapacita()));
                holder.tipoAttrezzo.setSelection(holder.adapter.getPosition(tipoAttrezzoStringFormat));
            }
        });

        //Gestione aggiornamento attrezzo
        holder.conferma.setVisibility(View.INVISIBLE);
        holder.conferma.setOnClickListener(v -> {

            if(holder.nomeAttrezzo.getText().toString().equals("") ||
                    holder.capacitaAttrezzo.getText().toString().equals("")) {
                Snackbar
                        .make(holder.itemView,"Dati inseriti inaccettabili", BaseTransientBottomBar.LENGTH_SHORT)
                        .show();
            }
            else{
                String nome = holder.nomeAttrezzo.getText().toString();
                double capacita = Double.parseDouble(holder.capacitaAttrezzo.getText().toString());
                String tipo = holder.tipoAttrezzo.getSelectedItem().toString();

                Attrezzo attrezzo = new Attrezzo(nome, TipoAttrezzo.valueOf(tipo.toUpperCase()), capacita);
                attrezzo.setId(listaAttrezzi.get(position).getId());
                attrezziViewModel.updateAttrezzo(attrezzo);

                holder.setModifica(true);
                holder.modifica.setImageResource(R.drawable.edit_24px);
            }
        });

        //Gestione operazione di cancellazione
        holder.cancella.setOnClickListener(v -> {
            String nome = holder.nomeAttrezzo.getText().toString();
            double capacita = Double.parseDouble(holder.capacitaAttrezzo.getText().toString());
            String tipo = holder.tipoAttrezzo.getSelectedItem().toString();

            Attrezzo attrezzo = new Attrezzo(nome, TipoAttrezzo.valueOf(tipo.toUpperCase()), capacita);
            attrezzo.setId(listaAttrezzi.get(position).getId());
            attrezziViewModel.deleteAttrezzo(attrezzo);
        });
    }

    @Override
    public int getItemCount() {
        return listaAttrezzi.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final EditText nomeAttrezzo;
        private final EditText capacitaAttrezzo;
        private final Spinner tipoAttrezzo;
        private final ImageButton cancella;
        private final ImageButton modifica;
        private final ImageButton conferma;
        private final ArrayAdapter<CharSequence> adapter;
        private boolean isModifica = true;

        public ViewHolder(View view) {
            super(view);

            nomeAttrezzo = view.findViewById(R.id.oneRowCard_nomeReale);
            capacitaAttrezzo = view.findViewById(R.id.oneRowCard_capacitaReale);
            tipoAttrezzo = view.findViewById(R.id.oneRowCard_tipoReale);
            cancella = view.findViewById(R.id.oneRowCard_imageCancella);
            modifica = view.findViewById(R.id.oneRowCard_imageButtonModifica);
            conferma = view.findViewById(R.id.oneRowCard_conferma);
            adapter = ArrayAdapter.createFromResource(
                    view.getContext(),
                    R.array.attrezzi,
                    android.R.layout.simple_spinner_item
            );
        }

        public void setModifica(boolean modifica) {
            this.isModifica = modifica;
        }
    }

    public void setDataList(List<Attrezzo> newDataList) {
        this.listaAttrezzi = newDataList;
        notifyDataSetChanged();
    }
}
