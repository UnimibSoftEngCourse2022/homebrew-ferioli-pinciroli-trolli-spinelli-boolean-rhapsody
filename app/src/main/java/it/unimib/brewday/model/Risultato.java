package it.unimib.brewday.model;

import java.util.List;

public class Risultato {


    public Risultato() {
        //serve per risultati senza ritorno di tipo
    }
    public boolean isSuccessful() {
        return this instanceof IngredientiSuccesso ||
                this instanceof IngredienteSuccesso;

    }

    public static final class Successo extends Risultato{

    }

    public static final class IngredienteSuccesso extends Risultato {
        private final Ingrediente ingrediente;



        public IngredienteSuccesso(Ingrediente ingrediente) {
            this.ingrediente = ingrediente;
        }

        public Ingrediente getData(){
            return ingrediente;
        }
    }

    public static final class IngredientiSuccesso extends Risultato {
        private final List<Ingrediente> listaIngrediente;

        public IngredientiSuccesso(List<Ingrediente> listaIngrediente) {
            this.listaIngrediente = listaIngrediente;
        }

        public List<Ingrediente> getData(){
            return listaIngrediente;
        }
    }

    public static final class AttrezziSuccesso extends Risultato {
        private final List<Attrezzo> attrezzi;

        public AttrezziSuccesso(List<Attrezzo> attrezzo) {
            this.attrezzi = attrezzo;
        }

        public List<Attrezzo> getAttrezzi() {
            return attrezzi;
        }
    }


    public static final class Errore extends Risultato {
        private final String message;

        public Errore(String message) {
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
    }
}
