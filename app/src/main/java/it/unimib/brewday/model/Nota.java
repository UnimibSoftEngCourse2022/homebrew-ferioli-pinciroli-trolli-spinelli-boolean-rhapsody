package it.unimib.brewday.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Nota {
    public Nota(String commento) {
        this.commento = commento;
    }
    @PrimaryKey(autoGenerate = true)
    long id;

    String commento;

}
