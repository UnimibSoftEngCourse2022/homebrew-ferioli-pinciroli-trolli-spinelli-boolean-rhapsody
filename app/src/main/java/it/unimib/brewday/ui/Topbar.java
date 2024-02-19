package it.unimib.brewday.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import it.unimib.brewday.R;

public class Topbar {
    private Topbar(){}
    public static void gestisciTopbar(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.arrow_back_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
