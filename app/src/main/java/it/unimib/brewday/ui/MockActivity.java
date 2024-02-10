package it.unimib.brewday.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
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

        Ricetta ricetta = new Ricetta("Birra della scimmia", 1);
        List<IngredienteRicetta> listaIngredienti = new ArrayList<>();
        listaIngredienti.add(new IngredienteRicetta(TipoIngrediente.ACQUA, 1));
        listaIngredienti.add(new IngredienteRicetta(TipoIngrediente.MALTO, 3));
        listaIngredienti.add(new IngredienteRicetta(TipoIngrediente.LUPPOLO, 5));
        listaIngredienti.add(new IngredienteRicetta(TipoIngrediente.LIEVITI, 4));
        listaIngredienti.add(new IngredienteRicetta(TipoIngrediente.ADDITIVI, 1));
        listaIngredienti.add(new IngredienteRicetta(TipoIngrediente.ZUCCHERO, 100));

        ricetteRepository.insertRicetta(ricetta, listaIngredienti, risultato -> {
            if(risultato.isSuccessful()){
                textView.setText("Ha funzionato");
                ricetteRepository.getRicette(risultatoInnestato -> {
                    if(risultatoInnestato.isSuccessful()){
                        List<Ricetta> listaRicette = ((Risultato.ListaRicetteSuccesso) risultatoInnestato).getRicette();
                        textView2.setText(listaRicette.get(0).getNome());
                    }
                });
                ricetteRepository.getIngredientiDellaRicetta(5, risultatoInnestato -> {
                    if(risultatoInnestato.isSuccessful()){
                        List<IngredienteRicetta> listaIngredientiDellaRicetta = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultatoInnestato).getListaIngrediente();
                        textView.setText(listaIngredientiDellaRicetta.get(0).getTipoIngrediente().getNome());
                    }
                });
            }
            else{
                textView.setText(((Risultato.Errore) risultato).getMessaggio());
            }
        });

        IngredienteRicetta ingredienteRicetta = new IngredienteRicetta(3, TipoIngrediente.ACQUA, 123912938);
        ricetteRepository.updateIngredientiRicetta(ingredienteRicetta, risultato -> {
            if(risultato.isSuccessful()){
                textView3.setText("dovrebbe andare");
            }
        });

    }
}