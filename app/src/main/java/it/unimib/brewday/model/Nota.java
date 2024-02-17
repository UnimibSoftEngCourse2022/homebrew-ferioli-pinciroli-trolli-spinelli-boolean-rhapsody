package it.unimib.brewday.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
