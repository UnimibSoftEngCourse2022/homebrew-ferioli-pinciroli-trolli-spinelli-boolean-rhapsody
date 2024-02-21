package it.unimib.brewday.ui.visualizza_birre;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.NotaDegustazione;
import it.unimib.brewday.model.Risultato;


public class NoteDegustazioneDialog extends DialogFragment {
    private Birra birra;
    private VisualizzaBirreViewModel visualizzaBirreViewModel;

    public NoteDegustazioneDialog(VisualizzaBirreViewModel visualizzaBirreViewModel, Birra birra) {
        this.birra = birra;
        this.visualizzaBirreViewModel = visualizzaBirreViewModel;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.fragment_note_degustazione_dialog, null);
        builder.setView(view);

        EditText nomeNota = view.findViewById(R.id.editTextText_nomeNotaDegustazione);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar_notaDegustazione);
        EditText commentoEditText = view.findViewById(R.id.editText_commentoNotaDegustazione);
        Button bottoneSalva = view.findViewById(R.id.button_salvaNotaDegustazione);

        bottoneSalva.setOnClickListener(v -> {

            if(ratingBar.getRating() == 0){

                Snackbar.make(view, "Devi inserire la valutazione", BaseTransientBottomBar.LENGTH_SHORT);
            }else {
                NotaDegustazione notaDegustazione = new NotaDegustazione(ratingBar.getRating(), nomeNota.getText().toString(), commentoEditText.getText().toString(), birra.getId());
                visualizzaBirreViewModel.creaNotaDegustazione(notaDegustazione);

                visualizzaBirreViewModel.getInserimentoNotaDegustazioneRisultato().observe(this, risultato -> {
                    if (risultato.isSuccessful()) {
                        visualizzaBirreViewModel.getNoteDegustazione(birra.getId());
                        this.dismiss();
                    } else {
                        Snackbar.make(view, ((Risultato.Errore) risultato).getMessaggio(), BaseTransientBottomBar.LENGTH_SHORT);
                    }
                });


            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return alertDialog;
    }

}