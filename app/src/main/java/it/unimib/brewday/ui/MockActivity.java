package it.unimib.brewday.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.IngredienteDellaRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.RicettaIngrediente;
import it.unimib.brewday.model.Risultato;
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
        RicettaIngrediente ricettaIngrediente = new RicettaIngrediente(0, TipoIngrediente.ACQUA, 20);

        ricetteRepository.upsertRicetta(r, risultato -> {
            if (risultato.isSuccessful()) {
                Log.d("TestMain", "Funziona inserimento ricetta");
            }
            else{
                Log.d("TestMain", "Non funziona inserimento ricetta");
            }
        });
    }
}