package it.unimib.brewday.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

@Entity(
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
                )
        }
)
public class IngredienteRicetta {
    private long idRicetta;
    @NonNull private TipoIngrediente tipoIngrediente;
    private double dosaggioIngrediente;

    public IngredienteRicetta(long idRicetta, @NonNull TipoIngrediente tipoIngrediente, double dosaggioIngrediente) {
        this.idRicetta = idRicetta;
        this.tipoIngrediente = tipoIngrediente;
        this.dosaggioIngrediente = dosaggioIngrediente;
    }

    @Ignore
    public IngredienteRicetta(@NonNull TipoIngrediente tipoIngrediente, double dosaggioIngrediente){
        this.idRicetta = -1;
        this.tipoIngrediente = tipoIngrediente;
        this.dosaggioIngrediente = dosaggioIngrediente;
    }

    public long getIdRicetta() {
        return idRicetta;
    }

    public void setIdRicetta(long idRicetta) {
        this.idRicetta = idRicetta;
    }

    public TipoIngrediente getTipoIngrediente() {
        return tipoIngrediente;
    }

    public void setTipoIngrediente(TipoIngrediente tipoIngrediente) {
        this.tipoIngrediente = tipoIngrediente;
    }

    public double getDosaggioIngrediente() {
        return dosaggioIngrediente;
    }
    public String getDosaggioIngredienteToString() {
        return Double.toString(dosaggioIngrediente);
    }

    public void setDosaggioIngrediente(double dosaggioIngrediente) {
        this.dosaggioIngrediente = dosaggioIngrediente;
    }
}
