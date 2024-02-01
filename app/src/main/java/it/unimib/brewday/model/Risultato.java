package it.unimib.brewday.model;

import java.util.List;

public abstract class Risultato {

    private Risultato() {}

    public boolean isSuccessful() {
        return this instanceof AttrezziSuccess ||
                this instanceof Success;
    }

    public static final class Success extends Risultato {}

    public static final class AttrezziSuccess extends Risultato {
        private final List<Attrezzo> attrezzi;

        public AttrezziSuccess(List<Attrezzo> attrezzo) {
            this.attrezzi = attrezzo;
        }

        public List<Attrezzo> getAttrezzi() {
            return attrezzi;
        }
    }

    public static final class Errore extends Risultato {
        private final Exception exception;

        public Errore(Exception e) {
            exception = e;
        }

        public String getErrorMessage() {
            return exception.getMessage();
        }
    }

}
