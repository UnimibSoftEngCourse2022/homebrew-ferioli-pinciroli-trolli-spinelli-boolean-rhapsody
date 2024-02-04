package it.unimib.brewday.model;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ingrediente {

    @PrimaryKey(autoGenerate = true) private long id;
    private TipoIngrediente tipo;
    private Double quantitàPosseduta;


    public Ingrediente(long id, TipoIngrediente tipo, Double quantitàPosseduta) {
        this.id = id;
        this.tipo = tipo;
        this.quantitàPosseduta = quantitàPosseduta;
    }
    @Ignore
    public Ingrediente(TipoIngrediente tipo) {
        this.tipo = tipo;
        this.quantitàPosseduta = 0.0;
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

    public Double getQuantitàPosseduta() {
        return quantitàPosseduta;
    }
    public String getQuantitaAssolutaToString(){
        return Double.toString(quantitàPosseduta);
    }

    public void setQuantitàPosseduta(Double quantitàPosseduta) {
        this.quantitàPosseduta = quantitàPosseduta;
    }
}