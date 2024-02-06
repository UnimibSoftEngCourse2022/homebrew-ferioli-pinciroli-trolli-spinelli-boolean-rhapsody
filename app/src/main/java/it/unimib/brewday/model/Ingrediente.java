package it.unimib.brewday.model;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ingrediente {

    @PrimaryKey(autoGenerate = true) private long id;
    private TipoIngrediente tipo;
    private Double quantitaPosseduta;


    public Ingrediente(long id, TipoIngrediente tipo, Double quantitaPosseduta) {
        this.id = id;
        this.tipo = tipo;
        this.quantitaPosseduta = quantitaPosseduta;
    }
    @Ignore
    public Ingrediente(TipoIngrediente tipo) {
        this.tipo = tipo;
        this.quantitaPosseduta = 0.0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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