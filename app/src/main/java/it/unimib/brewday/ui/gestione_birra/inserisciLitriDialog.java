package it.unimib.brewday.ui.gestione_birra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.brewday.R;


public class inserisciLitriDialog extends DialogFragment {


    public inserisciLitriDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.fragment_inserisci_litri_dialog, null);
        builder.setView(view);


        return super.onCreateDialog(savedInstanceState);
    }
}