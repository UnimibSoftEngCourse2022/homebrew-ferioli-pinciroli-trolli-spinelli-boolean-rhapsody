package it.unimib.brewday.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import it.unimib.brewday.R;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.RicettaIngrediente;
import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.repository.RicetteRepository;
import it.unimib.brewday.util.ServiceLocator;

public class MockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        RicetteRepository ricetteRepository = ServiceLocator.getInstance().getRicetteRepository(this);

        Ricetta r = new Ricetta("Birra della scimmia");
        RicettaIngrediente ricettaIngrediente = new RicettaIngrediente(1, TipoIngrediente.ACQUA, 20);

        ricetteRepository.upsertRicetta(r, risultato -> {
            if (risultato.isSuccessful()) {
                Log.d("TestMain", "Funziona inserimento ricetta");

                ricetteRepository.upsertRicettaIngrediente(ricettaIngrediente, risultato1 -> {
                    if (risultato1.isSuccessful()) {
                        Log.d("TestMain", "Funziona inserimento ingrediente");
                    }
                    else{
                        Log.d("TestMain", "Non funziona inserimento ingrediente");
                    }
                });
            }
            else{
                Log.d("TestMain", "Non funziona inserimento ricetta");
            }
        });
    }
}