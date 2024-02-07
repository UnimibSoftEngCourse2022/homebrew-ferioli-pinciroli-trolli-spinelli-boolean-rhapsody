package it.unimib.brewday.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "ricette_ingredienti",
        primaryKeys = {"idRicetta", "tipoIngrediente"},
        foreignKeys = {
                @ForeignKey(
                        entity = Ricetta.class,
                        parentColumns = "id",
                        childColumns = "idRicetta",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Ingrediente.class,
                        parentColumns = "tipo",
                        childColumns = "tipoIngrediente",
                        onDelete = ForeignKey.CASCADE
                )})
public class RicettaIngrediente {

    @ColumnInfo(name = "id_ricetta")
    public long idRicetta;

    @ColumnInfo(name = "tipo_ingrediente")
    public TipoIngrediente tipoIngrediente;

    @ColumnInfo(name = "grammi_per_litro")
    public double grammiPerLitro;
}
