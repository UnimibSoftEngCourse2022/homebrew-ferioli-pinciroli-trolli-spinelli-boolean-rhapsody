package it.unimib.brewday.ui.gestisci_ingredienti;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

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
                        ingrediente.setQuantitaPosseduta(ingrediente.getQuantitaPosseduta() + 0.1);
                        ingredienteViewModel.updateIngrediente(ingrediente);
                    }

                    @Override
                    public void onRemoveIngredienteClick(Ingrediente ingrediente, int position) {
                        if (ingrediente.getQuantitaPosseduta() < 0.1) {
                            Snackbar.make(view, "Non si può avere ingredienti negativi", LENGTH_SHORT).show();
                        } else {
                            ingrediente.setQuantitaPosseduta(ingrediente.getQuantitaPosseduta() - 0.1);
                            ingredienteViewModel.updateIngrediente(ingrediente);
                        }

                    }
                }, (ingrediente, quantitaIngrediente, position) -> quantitaIngrediente.setOnKeyListener((v, keyCode, event) -> {
                            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                                verificaIngrediente(quantitaIngrediente);
                                ingrediente.setQuantitaPosseduta(Double.valueOf(String.valueOf(quantitaIngrediente.getText())));
                                ingredienteViewModel.updateIngrediente(ingrediente);
                            }
                            return false;
                        }));



                listViewIngredientiDispobili.setAdapter(adapterListViewListaIngredientiDisponibili);
                listViewIngredientiDispobili.setDivider(null);
            }else{
                Snackbar.make(view, ((Risultato.Error) risultato).getMessage(), LENGTH_SHORT).show();
            }
         });
    }

    public void verificaIngrediente(EditText quantitaIngrediente){
        if (quantitaIngrediente.getText().length() == 0) {
            quantitaIngrediente.setText("0.0");
        } else if (quantitaIngrediente.getText().toString().startsWith(".")) {
            quantitaIngrediente.setText("0" + quantitaIngrediente.getText().toString());
        } else if (quantitaIngrediente.getText().toString().endsWith(".")) {
            quantitaIngrediente.setText(quantitaIngrediente.getText().toString() + "0");
        } else if (!(quantitaIngrediente.getText().toString().contains("."))) {
            quantitaIngrediente.setText(quantitaIngrediente.getText().toString() + ".0");
        }
    }
}