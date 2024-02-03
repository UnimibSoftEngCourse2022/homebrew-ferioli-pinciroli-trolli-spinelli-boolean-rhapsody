package it.unimib.brewday.model;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ingrediente {

    @PrimaryKey(autoGenerate = true) private long id;
    private TipoIngrediente tipo;
    private Double quantitaAssoluta;


    public Ingrediente(long id, TipoIngrediente tipo, Double quantitaAssoluta) {
        this.id = id;
        this.tipo = tipo;
        this.quantitaAssoluta = quantitaAssoluta;
    }
    @Ignore
    public Ingrediente(TipoIngrediente tipo) {
        this.tipo = tipo;
        this.quantitaAssoluta = 0.0;
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

    public Double getQuantitaAssoluta() {
        return quantitaAssoluta;
    }
    public String getQuantitaAssolutaToString(){
        return Double.toString(quantitaAssoluta);
    }

    public void setQuantitaAssoluta(Double quantitaAssoluta) {
        this.quantitaAssoluta = quantitaAssoluta;
    }
}