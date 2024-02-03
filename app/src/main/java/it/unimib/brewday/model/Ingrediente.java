package it.unimib.brewday.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingrediente {

    @PrimaryKey public long id;

    public Ingrediente() {
        //placeholder
    }
}