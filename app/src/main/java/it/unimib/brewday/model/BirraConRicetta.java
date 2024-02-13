package it.unimib.brewday.model;

public class BirraConRicetta {

    private long id;
    private int litriProdotti;
    private long idRicetta;
    private boolean terminata;
    private String nomeRicetta;

    public BirraConRicetta(long id, int litriProdotti, long idRicetta, boolean terminata, String nomeRicetta) {
        this.id = id;
        this.litriProdotti = litriProdotti;
        this.idRicetta = idRicetta;
        this.terminata = terminata;
        this.nomeRicetta = nomeRicetta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLitriProdotti() {
        return litriProdotti;
    }

    public void setLitriProdotti(int litriProdotti) {
        this.litriProdotti = litriProdotti;
    }

    public long getIdRicetta() {
        return idRicetta;
    }

    public void setIdRicetta(long idRicetta) {
        this.idRicetta = idRicetta;
    }

    public boolean isTerminata() {
        return terminata;
    }

    public void setTerminata(boolean terminata) {
        this.terminata = terminata;
    }

    public String getNomeRicetta() {
        return nomeRicetta;
    }

    public void setNomeRicetta(String nomeRicetta) {
        this.nomeRicetta = nomeRicetta;
    }
}
