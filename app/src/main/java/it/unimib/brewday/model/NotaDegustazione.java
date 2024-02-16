package it.unimib.brewday.model;

public class NotaDegustazione extends Nota{
    public NotaDegustazione(double recensione, String recensore) {
        super("");
        this.recensione = recensione;
        this.recensore = recensore;
    }
    public NotaDegustazione(double recensione, String recensore, String commento) {
        super(commento);
        this.recensione = recensione;
        this.recensore = recensore;
    }

    double recensione;
    String recensore;
}
