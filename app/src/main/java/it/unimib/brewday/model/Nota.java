package it.unimib.brewday.model;


public class Nota {
    public Nota(String commento) {
        this.commento = commento;
    }

    private String commento;

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }
}
