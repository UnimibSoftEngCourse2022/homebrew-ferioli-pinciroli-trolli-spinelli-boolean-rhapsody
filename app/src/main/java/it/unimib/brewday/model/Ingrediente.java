package it.unimib.brewday.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredienti")
public class Ingrediente {

    @PrimaryKey
    @ColumnInfo(name = "tipo")
    private TipoIngrediente tipo;

    @ColumnInfo(name = "quantita_posseduta")
    private Double quantitaPosseduta;


    public Ingrediente(TipoIngrediente tipo, Double quantitaPosseduta) {
        this.tipo = tipo;
        this.quantitaPosseduta = quantitaPosseduta;
    }
    @Ignore
    public Ingrediente(TipoIngrediente tipo) {
        this.tipo = tipo;
        this.quantitaPosseduta = 0.0;
    }

    public TipoIngrediente getTipo() {
        return tipo;
    }

    public void setTipo(TipoIngrediente nome) {
        this.tipo = nome;
    }

    public Double getQuantitaPosseduta() {
        return quantitaPosseduta;
    }

    public String getQuantitaAssolutaToString(){
        return Double.toString(quantitaPosseduta);
    }

    public void setQuantitaPosseduta(Double quantitaPosseduta) {
        this.quantitaPosseduta = quantitaPosseduta;
    }
}