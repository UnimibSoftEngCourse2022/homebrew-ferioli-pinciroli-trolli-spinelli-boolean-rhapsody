package it.unimib.brewday.ui.gestione_birra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.ui.gestisci_ricette.RicettaDettagliataFragmentDirections;


public class InserisciLitriDialog extends DialogFragment {

    private Ricetta ricetta;
    private View fragmentRicettaView;

    public InserisciLitriDialog(Ricetta ricetta, View view) {
        this.ricetta = ricetta;
        this.fragmentRicettaView = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.fragment_inserisci_litri_dialog, null);
        builder.setView(view);


        EditText numeroLitriBirra = view.findViewById(R.id.editText_LitriDialog_numeroLitriBirra);
        Button annulla = view.findViewById(R.id.button_LitriDialog_Annulla);
        Button prepara = view.findViewById(R.id.button_LitriDialog_Prepara);



        annulla.setOnClickListener(v ->
            this.dismiss()
        );

        prepara.setOnClickListener(v -> {
            RicettaDettagliataFragmentDirections.ActionRicettaDettagliataFragmentToPreparaBirraFragment action =
                    RicettaDettagliataFragmentDirections.actionRicettaDettagliataFragmentToPreparaBirraFragment(ricetta, Integer.parseInt(numeroLitriBirra.getText().toString()));
            Navigation.findNavController(fragmentRicettaView).navigate(action);
            this.dismiss();
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return alertDialog;
    }
}