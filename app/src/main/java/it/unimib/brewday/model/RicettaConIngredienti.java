package it.unimib.brewday.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class RicettaConIngredienti {
    @Embedded public Ricetta ricetta;
    @Relation(
            parentColumn = "idRicetta",
            entityColumn = "tipoIngrediente",
            associateBy = @Junction(RicettaIngrediente.class)
    )
    public List<Ingrediente> ingredienti;
}
