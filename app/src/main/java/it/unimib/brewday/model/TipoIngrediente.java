package it.unimib.brewday.model;

public enum TipoIngrediente {
    ACQUA("Acqua"), MALTO("Malto"), LUPPOLO("Luppolo"), LIEVITI("Lieviti"), ZUCCHERO("Zucchero"), ADDITIVI("Additivi");

    private final String nome;

    TipoIngrediente(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
