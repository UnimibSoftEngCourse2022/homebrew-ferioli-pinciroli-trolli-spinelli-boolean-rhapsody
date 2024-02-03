package it.unimib.brewday.util;

import static it.unimib.brewday.util.Constants.ACQUA;
import static it.unimib.brewday.util.Constants.ADDITIVI;
import static it.unimib.brewday.util.Constants.LIEVITI;
import static it.unimib.brewday.util.Constants.LUPPOLO;
import static it.unimib.brewday.util.Constants.MALTO;
import static it.unimib.brewday.util.Constants.ZUCCHERO;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.R;
import it.unimib.brewday.model.Ingrediente;

public class ListaIngredienti {

    List<Ingrediente> listaIngredientiDisponibili = new ArrayList<>();
 public List<Ingrediente> getListaIngredienti(){
     listaIngredientiDisponibili = new ArrayList<>();
     listaIngredientiDisponibili.add(new Ingrediente(ACQUA));
     listaIngredientiDisponibili.add(new Ingrediente(MALTO));
     listaIngredientiDisponibili.add(new Ingrediente(LUPPOLO));
     listaIngredientiDisponibili.add(new Ingrediente(LIEVITI));
     listaIngredientiDisponibili.add(new Ingrediente(ZUCCHERO));
     listaIngredientiDisponibili.add(new Ingrediente(ADDITIVI));
     return listaIngredientiDisponibili;


 }

}
