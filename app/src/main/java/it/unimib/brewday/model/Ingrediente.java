package it.unimib.brewday.model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ingrediente {

    @PrimaryKey @NonNull
    private TipoIngrediente tipo;

    private int quantitaPosseduta;

    @Ignore
    public Ingrediente(TipoIngrediente tipo, int quantitaPosseduta) {
        this.tipo = tipo;
        this.quantitaPosseduta = quantitaPosseduta;
    }

    public Ingrediente(TipoIngrediente tipo) {
        this.tipo = tipo;
        this.quantitaPosseduta = 0;
    }

    public TipoIngrediente getTipo() {
        return tipo;
    }

    public void setTipo(TipoIngrediente nome) {
        this.tipo = nome;
    }

    public int getQuantitaPosseduta() {
        return quantitaPosseduta;
    }

    public String getQuantitaAssolutaToString(){
        return Integer.toString(quantitaPosseduta);
    }

    public void setQuantitaPosseduta(int quantitaPosseduta) {
        this.quantitaPosseduta = quantitaPosseduta;
    }
}