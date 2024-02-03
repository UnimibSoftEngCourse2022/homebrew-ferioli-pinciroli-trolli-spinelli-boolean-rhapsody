package it.unimib.brewday.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.unimib.brewday.AdapterListViewListaIngredientiDisponibili;
import it.unimib.brewday.R;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.Ingrediente;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientiFragment extends Fragment {

    ListView listViewIngredientiDispobili;

    IngredienteViewModel ingredienteViewModel;

    AdapterListViewListaIngredientiDisponibili adapterListViewListaIngredientiDisponibili;

    List<Ingrediente> listaIngredienti;




    public IngredientiFragment() {
        // Required empty public constructor
    }


    public static IngredientiFragment newInstance() {

        return new IngredientiFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredienteViewModel = new ViewModelProvider(this,
                new IngredienteViewModelFactory(getContext()))
                .get(IngredienteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredienti, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewIngredientiDispobili = view.findViewById(R.id.listView_ingredrientiDisponibili);

         ingredienteViewModel.readAllIngredienti();
         ingredienteViewModel.readAllIngredientiMutableLiveData.observe(getViewLifecycleOwner(), risultato -> {
            if(risultato.isSuccessful()){
                listaIngredienti = ((Risultato.IngredientiSuccess) risultato).getData();
                adapterListViewListaIngredientiDisponibili = new AdapterListViewListaIngredientiDisponibili(getContext(), 0, listaIngredienti, R.layout.lista_ingredienti_singoli, new AdapterListViewListaIngredientiDisponibili.OnItemClickListener() {
                    @Override
                    public void onAddIngredienteClick(Ingrediente ingrediente, int position) {
                        ingrediente.setQuantitaAssoluta(ingrediente.getQuantitaAssoluta() + 1.0);
                        ingredienteViewModel.updateIngrediente(ingrediente);
                    }

                    @Override
                    public void onRemoveIngredienteClick(Ingrediente ingrediente, int position) {
                        if (ingrediente.getQuantitaAssoluta() < 1.0) {
                            Snackbar.make(view, "Non si può avere ingredienti negativi", Snackbar.LENGTH_SHORT).show();
                        } else {
                            ingrediente.setQuantitaAssoluta(ingrediente.getQuantitaAssoluta() - 1.0);
                            ingredienteViewModel.updateIngrediente(ingrediente);
                        }

                    }
                }, (ingrediente, quantitaIngrediente, position) -> {
                    quantitaIngrediente.setOnKeyListener((v, keyCode, event) -> {
                        if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                            if(quantitaIngrediente.getText().length() == 0) {
                                quantitaIngrediente.setText("0.0");
                            }else if(quantitaIngrediente.getText().toString().startsWith(".")){
                                quantitaIngrediente.setText("0" + quantitaIngrediente.getText().toString());
                            }else if(quantitaIngrediente.getText().toString().endsWith(".")){
                                quantitaIngrediente.setText( quantitaIngrediente.getText().toString() + "0");
                            } else if (!(quantitaIngrediente.getText().toString().contains("."))) {
                                quantitaIngrediente.setText( quantitaIngrediente.getText().toString() + ".0");
                            }


                            ingrediente.setQuantitaAssoluta(Double.valueOf(String.valueOf(quantitaIngrediente.getText())));
                            ingredienteViewModel.updateIngrediente(ingrediente);
                        }
                        return false;
                    });

                });

                listViewIngredientiDispobili.setAdapter(adapterListViewListaIngredientiDisponibili);

            }else{
                Snackbar.make(view, ((Risultato.Error) risultato).getMessage(), Snackbar.LENGTH_SHORT).show();
            }
         });
    }
}