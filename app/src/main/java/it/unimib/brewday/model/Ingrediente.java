package it.unimib.brewday.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingrediente {

    @PrimaryKey(autoGenerate = true) private long id;
    private String nome;

    private Double quantitaAssoluta;

    public Ingrediente() {
        //placeholder
    }

    public Ingrediente(long id, String nome, Double quantitaAssoluta) {
        this.id = id;
        this.nome = nome;
        this.quantitaAssoluta = quantitaAssoluta;
    }

    public Ingrediente(String nome) {
        this.nome = nome;
        this.quantitaAssoluta = 0.0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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