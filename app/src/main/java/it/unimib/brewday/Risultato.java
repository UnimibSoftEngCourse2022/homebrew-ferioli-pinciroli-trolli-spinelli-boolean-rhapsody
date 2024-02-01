package it.unimib.brewday;

import java.util.List;

import it.unimib.brewday.model.Ingrediente;

public class Risultato {


    public Risultato() {
    }
    public boolean isSuccessful() {
        return this instanceof IngredientiSuccess ||
                this instanceof IngredienteSuccess;

    }

    public static final class Success extends Risultato{}
    public static final class IngredienteSuccess extends Risultato {
        private final Ingrediente ingrediente;



        public IngredienteSuccess(Ingrediente ingrediente) {
            this.ingrediente = ingrediente;
        }

        public Ingrediente getData(){
            return ingrediente;
        }
    }

    public static final class IngredientiSuccess extends Risultato {
        private final List<Ingrediente> listaIngrediente;


        public IngredientiSuccess(List<Ingrediente> listaIngrediente) {
            this.listaIngrediente = listaIngrediente;
        }

        public List<Ingrediente> getData(){
            return listaIngrediente;
        }
    }


    public static final class Error extends Risultato {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
    }
}
