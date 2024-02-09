package it.unimib.brewday.model;

import java.util.List;

public class Risultato {


    private Risultato() {
        //costruttore privato
    }

    public boolean isSuccessful() {
        return this instanceof Successo
                || this instanceof IngredientiSuccesso
                || this instanceof IngredienteSuccesso
                || this instanceof AttrezziSuccesso
                || this instanceof IngredientiDellaRicettaSuccesso;
//                || this instanceof ListaRicetteSuccesso;

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

    public static final class IngredientiDellaRicettaSuccesso extends Risultato {
        private final List<IngredienteDellaRicetta> listaIngrediente;

        public IngredientiDellaRicettaSuccesso(List<IngredienteDellaRicetta> listaIngrediente) {
            this.listaIngrediente = listaIngrediente;
        }

        public List<IngredienteDellaRicetta> getData(){
            return listaIngrediente;
        }
    }

//    public static final class ListaRicetteSuccesso extends Risultato {
//        private final List<RicettaConIngredienti> ricette;
//
//        public ListaRicetteSuccesso(List<RicettaConIngredienti> listaRicette){
//            ricette = listaRicette;
//        }
//
//        public List<RicettaConIngredienti> getRicette(){
//            return ricette;
//        }
//
//    }
//
//    public static final class SingolaRicettaSuccesso extends Risultato {
//        private final RicettaConIngredienti ricetta;
//
//        public SingolaRicettaSuccesso(RicettaConIngredienti ricetta){
//            this.ricetta = ricetta;
//        }
//
//        public RicettaConIngredienti getRicetta(){
//            return ricetta;
//        }
//    }

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
