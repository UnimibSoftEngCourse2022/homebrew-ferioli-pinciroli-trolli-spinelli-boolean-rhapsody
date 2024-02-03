package it.unimib.brewday.util;



import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.TipoIngrediente;

public class ListaIngredienti {

    List<Ingrediente> listaIngredientiDisponibili = new ArrayList<>();
 public List<Ingrediente> getListaIngredienti(){
     listaIngredientiDisponibili = new ArrayList<>();
     listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.ACQUA));
     listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.MALTO));
     listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.LUPPOLO));
     listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.LIEVITI));
     listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.ZUCCHERO));
     listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.ADDITIVI));
     return listaIngredientiDisponibili;


 }

}
