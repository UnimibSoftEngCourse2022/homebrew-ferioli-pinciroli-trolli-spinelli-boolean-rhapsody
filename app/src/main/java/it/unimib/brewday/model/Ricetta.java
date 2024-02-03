package it.unimib.brewday.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ricetta {

    @PrimaryKey public long id;

    public Ricetta() {
        //placeholder
    }
}