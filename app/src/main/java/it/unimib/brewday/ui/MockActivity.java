package it.unimib.brewday.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import it.unimib.brewday.R;
import it.unimib.brewday.repository.RicetteRepository;
import it.unimib.brewday.util.ServiceLocator;

public class MockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        RicetteRepository ricetteRepository = ServiceLocator.getInstance().getRicetteRepository(this);

//        Ricetta r = new Ricetta("Birra della scimmia");
//        RicettaIngrediente ricettaIngrediente = new RicettaIngrediente(1, TipoIngrediente.ACQUA, 20);
//
//        ricetteRepository.insertRicetta(r, risultato -> {
//            if (risultato.isSuccessful()) {
//                Log.d("TestMain", "Funziona inserimento ricetta");
//
//                ricetteRepository.insertRicettaIngrediente(ricettaIngrediente, risultato1 -> {
//                    if (risultato1.isSuccessful()) {
//                        Log.d("TestMain", "Funziona inserimento ingrediente");
//
//                        ricetteRepository.getIngredientiDellaRicetta(1, risultato2 -> {
//                            if(risultato2.isSuccessful() && risultato2 instanceof Risultato.IngredientiDellaRicettaSuccesso){
//                                Iterator i = ((Risultato.IngredientiDellaRicettaSuccesso) risultato2).getData().iterator();
//                                while (i.hasNext()) {
//                                    Log.d("TestMain", "Ingrediente : " + i.next().toString());
//                                }
//                            }
//                            else{
//                                Log.d("TestMain", "Non funziona lettura ingrediente");
//                            }
//                        });
//                    }
//                    else{
//                        Log.d("TestMain", "Non funziona inserimento ingrediente");
//                    }
//                });
//            }
//            else{
//                Log.d("TestMain", "Non funziona inserimento ricetta");
//            }
//        });
    }
}