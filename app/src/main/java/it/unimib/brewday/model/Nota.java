package it.unimib.brewday.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Nota {
    public Nota(String commento) {
        this.commento = commento;
    }
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String commento;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }
}
