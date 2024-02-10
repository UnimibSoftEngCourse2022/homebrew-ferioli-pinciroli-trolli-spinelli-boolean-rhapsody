package it.unimib.brewday.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


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
                ricetteRepository.getRicette(risultatoInnestato -> {
                    if(risultatoInnestato.isSuccessful()){
                        List<Ricetta> listaRicette = ((Risultato.ListaRicetteSuccesso) risultatoInnestato).getRicette();
                    }
                });
                ricetteRepository.getIngredientiRicetta(1, risultatoInnestato -> {
                    if(risultatoInnestato.isSuccessful()){
                        List<IngredienteRicetta> listaIngredientiDellaRicetta = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultatoInnestato).getListaIngrediente();
                    }
                });
            }
        });

        /*
        List<IngredienteRicetta> ingredientiRicetta = new ArrayList();
        IngredienteRicetta ingredienteRicetta = new IngredienteRicetta(1,
                TipoIngrediente.ACQUA, 123912938);


        IngredienteRicetta ingredienteRicetta1 = new IngredienteRicetta(2,
                TipoIngrediente.ACQUA, 85973589);

        ingredientiRicetta.add(ingredienteRicetta);
        ingredientiRicetta.add(ingredienteRicetta1);
        ricetteRepository.updateIngredientiRicetta(ingredientiRicetta, risultato -> {
            if(risultato.isSuccessful()){

            }
        });
         */
    }
}