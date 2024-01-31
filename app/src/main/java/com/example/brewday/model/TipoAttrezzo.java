package com.example.brewday.model;

public enum TipoAttrezzo {
    FERMENTATORE("Fermentatore"), DISTILLATORE("Distillatore"), BOLLITORE("Bollitore");

    private final String nome;

    TipoAttrezzo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
