package it.unimib.brewday.ui.visualizza_birre;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import it.unimib.brewday.R;


public class NoteDegustazioneDialog extends DialogFragment {

    public NoteDegustazioneDialog() {
        // Required empty public constructor
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
            //Chiude la finestra del dialog
            this.dismiss();
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return alertDialog;
    }

}