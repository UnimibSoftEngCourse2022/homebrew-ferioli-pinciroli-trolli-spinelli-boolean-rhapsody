package it.unimib.brewday.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class BirraConRicetta {

    @Embedded public Birra birra;
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    public Ricetta nomeRicetta;
}
