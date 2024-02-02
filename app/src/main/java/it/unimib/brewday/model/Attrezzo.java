package it.unimib.brewday.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Attrezzo {

    @PrimaryKey public long id;

    public Attrezzo() {
        //placeholder
    }
}