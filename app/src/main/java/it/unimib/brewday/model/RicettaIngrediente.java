package it.unimib.brewday.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "ricetta_ingrediente",
        primaryKeys = {"idRicetta", "tipoIngrediente"},
        foreignKeys = {
                @ForeignKey(
                        entity = Ricetta.class,
                        parentColumns = "id",
                        childColumns = "idRicetta",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Ingrediente.class,
                        parentColumns = "tipo",
                        childColumns = "tipoIngrediente",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )})
public class RicettaIngrediente {

    @ColumnInfo(name = "id_ricetta")
    public long idRicetta;

    @ColumnInfo(name = "tipo_ingrediente")
    public TipoIngrediente tipoIngrediente;

    @ColumnInfo(name = "quantita_per_litro")
    public Double quantitaPerLitro;
}
