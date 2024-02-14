package it.unimib.brewday.model;

public class BirraConRicetta extends Birra {

    private String nomeRicetta;

    public BirraConRicetta(int litriProdotti, long idRicetta, String nomeRicetta) {
        super(litriProdotti, idRicetta);
        this.nomeRicetta = nomeRicetta;
    }

    public String getNomeRicetta() {
        return nomeRicetta;
    }

    public void setNomeRicetta(String nomeRicetta) {
        this.nomeRicetta = nomeRicetta;
    }
}
