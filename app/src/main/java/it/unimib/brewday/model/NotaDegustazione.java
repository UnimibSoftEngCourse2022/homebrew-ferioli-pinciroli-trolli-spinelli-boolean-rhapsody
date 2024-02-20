package it.unimib.brewday.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class NotaDegustazione{
    @Ignore
    public NotaDegustazione(float recensione, String recensore) {
        this.commento = "";
        this.recensione = recensione;
        this.recensore = recensore;
    }
    public NotaDegustazione(float recensione, String recensore, String commento, long idBirra) {
        this.commento = commento;
        this.recensione = recensione;
        this.recensore = recensore;
        this.idBirra = idBirra;
    }

    private float recensione;
    private String recensore;
    private String commento;
    private long idBirra;
    @PrimaryKey(autoGenerate = true)
    private long id;

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public float getRecensione() {
        return recensione;
    }

    public void setRecensione(float recensione) {
        this.recensione = recensione;
    }

    public String getRecensore() {
        return recensore;
    }

    public void setRecensore(String recensore) {
        this.recensore = recensore;
    }

    public long getIdBirra() {
        return idBirra;
    }

    public void setIdBirra(long idBirra) {
        this.idBirra = idBirra;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
