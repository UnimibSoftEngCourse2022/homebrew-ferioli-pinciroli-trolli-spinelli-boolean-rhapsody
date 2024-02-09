package it.unimib.brewday.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        ricetteRepository.upsertRicetta(new Ricetta("PROVA"), result -> {
            textView.setText(((Risultato.Errore) result).getMessage());
        });
        ricetteRepository.upsertRicettaIngrediente(new RicettaIngrediente(1, TipoIngrediente.ACQUA, 5), result -> {
            textView2.setText(((Risultato.Errore) result).getMessage());
        });
        ricetteRepository.getIngredientiDellaRicetta(1, result -> {
            if(result.isSuccessful()){
                List<IngredienteDellaRicetta> lista = ((Risultato.IngredientiDellaRicettaSuccesso)result).getData();
                textView3.setText(lista.get(0).getTipoIngrediente().getNome());
            }
            else{
                textView3.setText(((Risultato.Errore) result).getMessage());
            }
        });

    }
}