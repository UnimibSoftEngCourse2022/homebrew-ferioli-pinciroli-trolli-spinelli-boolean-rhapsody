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
                || this instanceof ListaIngredientiDellaRicettaSuccesso
                || this instanceof ListaRicetteSuccesso
                || this instanceof SingolaRicettaSuccesso
                || this instanceof AllBirreSuccesso;

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

    public static final class ListaIngredientiDellaRicettaSuccesso extends Risultato {
        private final List<IngredienteRicetta> listaIngrediente;

        public ListaIngredientiDellaRicettaSuccesso(List<IngredienteRicetta> listaIngrediente) {
            this.listaIngrediente = listaIngrediente;
        }

        public List<IngredienteRicetta> getListaIngrediente(){
            return listaIngrediente;
        }
    }

    public static final class ListaRicetteSuccesso extends Risultato {
        private final List<Ricetta> ricette;

        public ListaRicetteSuccesso(List<Ricetta> ricette){
            this.ricette = ricette;
        }

        public List<Ricetta> getRicette(){
            return ricette;
        }

    }

    public static final class SingolaRicettaSuccesso extends Risultato {
        private final long idRicetta;

        public SingolaRicettaSuccesso(long idRicetta){
            this.idRicetta = idRicetta;
        }

        public long getIdRicetta(){
            return idRicetta;
        }
    }

    public static final class AllBirreSuccesso extends Risultato {
        private final List<Birra> allBirre;

        public AllBirreSuccesso(List<Birra> allBirre) {
            this.allBirre = allBirre;
        }

        public List<Birra> getAllBirre() {
            return allBirre;
        }
    }

    public static final class Errore extends Risultato {
        private final String messaggio;

        public Errore(String messaggio) {
            this.messaggio = messaggio;
        }

        public String getMessaggio(){
            return messaggio;
        }
    }
}
