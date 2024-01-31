package com.example.brewday.model;

public abstract class Result {

    private Result() {}

    public boolean isSuccessful() {
        return this instanceof AttrezzoSuccess ||
                this instanceof Success;
    }

    public static class Success extends Result{}

    public static class AttrezzoSuccess extends Result{
        private final Attrezzo attrezzo;
        AttrezzoSuccess(Attrezzo attrezzo) {
            this.attrezzo = attrezzo;
        }

        public Attrezzo getAttrezzo() {
            return attrezzo;
        }
    }

}
