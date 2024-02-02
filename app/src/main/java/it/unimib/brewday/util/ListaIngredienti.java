package it.unimib.brewday.util;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Ingrediente;

public class ListaIngredienti {

    List<Ingrediente> listaIngredientiDisponibili = new ArrayList<>();
 public List<Ingrediente> getListaIngredienti(){
     listaIngredientiDisponibili = new ArrayList<>();
     listaIngredientiDisponibili.add(new Ingrediente("acqua" ));
     listaIngredientiDisponibili.add(new Ingrediente("malto" ));
     listaIngredientiDisponibili.add(new Ingrediente("luppolo" ));
     listaIngredientiDisponibili.add(new Ingrediente("lieviti" ));
     listaIngredientiDisponibili.add(new Ingrediente("zucchero" ));
     listaIngredientiDisponibili.add(new Ingrediente("additivi" ));

     return listaIngredientiDisponibili;


 }

}
