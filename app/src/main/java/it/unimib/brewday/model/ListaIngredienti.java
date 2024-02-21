package it.unimib.brewday.model;



import java.util.ArrayList;
import java.util.List;

public class ListaIngredienti {

    private List<Ingrediente> listaIngredientiDisponibili = new ArrayList<>();
    public List<Ingrediente> getListaIngredienti(){
        listaIngredientiDisponibili = new ArrayList<>();
        listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.ACQUA));
        listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.ADDITIVI));
        listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.LIEVITI));
        listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.LUPPOLO));
        listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.MALTO));
        listaIngredientiDisponibili.add(new Ingrediente(TipoIngrediente.ZUCCHERO));

        return listaIngredientiDisponibili;
    }

}
