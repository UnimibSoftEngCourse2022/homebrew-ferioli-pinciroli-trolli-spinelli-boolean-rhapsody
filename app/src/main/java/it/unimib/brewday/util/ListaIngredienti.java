package it.unimib.brewday.util;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Ingrediente;

public class ListaIngredienti {

    List<Ingrediente> listaIngredienti = new ArrayList<>();
 public List<Ingrediente> getListaIngredienti(){
     listaIngredienti = new ArrayList<>();
     listaIngredienti.add(new Ingrediente("acqua" ));
     listaIngredienti.add(new Ingrediente("malto" ));
     listaIngredienti.add(new Ingrediente("luppolo" ));
     listaIngredienti.add(new Ingrediente("lieviti" ));
     listaIngredienti.add(new Ingrediente("zucchero" ));
     listaIngredienti.add(new Ingrediente("additivi" ));

     return listaIngredienti;


 }

}
