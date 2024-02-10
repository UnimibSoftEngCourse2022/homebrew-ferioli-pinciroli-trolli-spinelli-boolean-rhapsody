package it.unimib.brewday.ui.gestisci_attrezzi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Attrezzo;
import it.unimib.brewday.model.TipoAttrezzo;

public class InserisciAttrezzoDialog extends DialogFragment {

    private final GestisciAttrezziViewModel gestisciAttrezziViewModel;

    public InserisciAttrezzoDialog(GestisciAttrezziViewModel gestisciAttrezziViewModel) {
        this.gestisciAttrezziViewModel = gestisciAttrezziViewModel;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.fragment_inserisci_attrezzo_dialog, null);
        builder.setView(view);

        //Componenti card
        EditText nomeAttrezzo = view.findViewById(R.id.inserisciAttrezzoDialog_inserisciNome);
        EditText capacitaAttrezzo = view.findViewById(R.id.inserisciAttrezzoDialog_inserisciCapacita);
        Spinner spinner = view.findViewById(R.id.inserisciAttrezzoDialog_spinner);
        Button confermaInserimento = view.findViewById(R.id.inserisciAttrezzoDialog_conferma);
        Button annullaInserimento = view.findViewById(R.id.inserisciAttrezzoDialog_annulla);

        //Gestione spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getContext(),
                R.array.attrezzi,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Gestione conferma inserimento dati per creazione attrezzo
        confermaInserimento.setOnClickListener(v -> {

            if(nomeAttrezzo.getText().toString().equals("") ||
                    capacitaAttrezzo.getText().toString().equals("")) {
                Snackbar
                        .make(view,R.string.attrezzi_dati_inaccettabili, BaseTransientBottomBar.LENGTH_SHORT)
                        .show();
            }
            else{
                String nome = nomeAttrezzo.getText().toString();
                double capacita = Integer.parseInt(capacitaAttrezzo.getText().toString());
                String tipo = spinner.getSelectedItem().toString();
                Attrezzo attrezzo = new Attrezzo(nome, TipoAttrezzo.valueOf(tipo.toUpperCase()), capacita);

                gestisciAttrezziViewModel.createAttrezzo(attrezzo);
                nomeAttrezzo.setText(null);
                capacitaAttrezzo.setText(null);
                spinner.setAdapter(adapter);

                //Chiude la finestra del dialog
                this.dismiss();
            }
        });

        annullaInserimento.setOnClickListener(v -> {
            nomeAttrezzo.setText(null);
            capacitaAttrezzo.setText(null);
            spinner.setAdapter(adapter);

            //Chiude la finestra del dialog
            this.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return alertDialog;
    }
}

