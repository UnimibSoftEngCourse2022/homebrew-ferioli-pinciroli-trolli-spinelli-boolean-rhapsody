package it.unimib.brewday.model;

import java.util.List;

public class Risultato {


    private Risultato() {
        //costruttore privato
    }

    public boolean isSuccessful() {
        return this instanceof Successo
                || this instanceof ListaIngredientiSuccesso
                || this instanceof IngredienteSuccesso
                || this instanceof ListaAttrezziSuccesso
                || this instanceof ListaIngredientiDellaRicettaSuccesso
                || this instanceof ListaRicetteSuccesso
                || this instanceof SingolaRicettaSuccesso
                || this instanceof AllBirreSuccesso
                || this instanceof ListaDifferenzaIngredientiSuccesso
                || this instanceof MassimizzazioneConsumoIngredientiSuccesso;

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

    public static final class ListaIngredientiSuccesso extends Risultato {
        private final List<Ingrediente> listaIngrediente;

        public ListaIngredientiSuccesso(List<Ingrediente> listaIngrediente) {
            this.listaIngrediente = listaIngrediente;
        }

        public List<Ingrediente> getData(){
            return listaIngrediente;
        }
    }

    public static final class ListaAttrezziSuccesso extends Risultato {
        private final List<Attrezzo> attrezzi;

        public ListaAttrezziSuccesso(List<Attrezzo> attrezzo) {
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
        private final List<BirraConRicetta> allBirre;

        public AllBirreSuccesso(List<BirraConRicetta> allBirre) {
            this.allBirre = allBirre;
        }

        public List<BirraConRicetta> getAllBirre() {
            return allBirre;
        }
    }

    public static final class ListaDifferenzaIngredientiSuccesso extends Risultato {
        private final List<Integer> listaDifferenzaIngredienti;

        public ListaDifferenzaIngredientiSuccesso(List<Integer> listaDifferenzaIngredienti) {
            this.listaDifferenzaIngredienti = listaDifferenzaIngredienti;
        }

        public List<Integer> getListaDifferenzaIngredienti() {
            return listaDifferenzaIngredienti;
        }
    }

    public static final class MassimizzazioneConsumoIngredientiSuccesso extends Risultato {
        private final Ricetta ricetta;
        private final int litri;

        public MassimizzazioneConsumoIngredientiSuccesso(Ricetta ricetta, int litri){
            this.ricetta = ricetta;
            this.litri = litri;
        }

        public Ricetta getRicetta() {
            return ricetta;
        }

        public int getLitri() {
            return litri;
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

    public static final class ErroreConSuggerimentoLitri extends Risultato {
        private final int litriSuggeriti;
        public ErroreConSuggerimentoLitri(int litriSuggeriti){
            this.litriSuggeriti = litriSuggeriti;
        }

        public int getLitriSuggeriti(){
            return litriSuggeriti;
        }
    }
}
