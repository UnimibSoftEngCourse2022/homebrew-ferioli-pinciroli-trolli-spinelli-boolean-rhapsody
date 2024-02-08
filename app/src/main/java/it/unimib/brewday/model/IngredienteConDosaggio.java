package it.unimib.brewday.model;

import androidx.room.Embedded;

public class IngredienteConDosaggio {
    @Embedded
    public Ingrediente ingrediente;
    public Integer dosaggio;
}
