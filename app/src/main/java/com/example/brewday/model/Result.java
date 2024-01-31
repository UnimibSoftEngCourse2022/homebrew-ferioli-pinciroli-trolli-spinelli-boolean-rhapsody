package com.example.brewday.model;

import java.util.List;

public abstract class Result {

    private Result() {}

    public boolean isSuccessful() {
        return this instanceof AttrezziSuccess ||
                this instanceof Success;
    }

    public static final class Success extends Result{}

    public static final class AttrezziSuccess extends Result{
        private final List<Attrezzo> attrezzi;

        public AttrezziSuccess(List<Attrezzo> attrezzo) {
            this.attrezzi = attrezzo;
        }

        public List<Attrezzo> getAttrezzi() {
            return attrezzi;
        }
    }

    public static final class Errore extends Result {
        private final Exception exception;

        public Errore(Exception e) {
            exception = e;
        }

        public String getErrorMessage() {
            return exception.getMessage();
        }
    }

}
