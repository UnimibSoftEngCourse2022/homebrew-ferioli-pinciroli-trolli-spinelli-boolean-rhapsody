package it.unimib.brewday.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class IngredienteDellaRicetta {
    @Embedded
    public Ingrediente ingrediente;

    @ColumnInfo(name = "quantita_per_litro")
    public Double quantitaPerLitro;
}
