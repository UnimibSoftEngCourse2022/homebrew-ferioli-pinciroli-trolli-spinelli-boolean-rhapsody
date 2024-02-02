package it.unimib.brewday.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingrediente {

    @PrimaryKey(autoGenerate = true) private long id;
    private String nome;

    private Double quantitàAssoluta;

    public Ingrediente() {
        //placeholder
    }

    public Ingrediente(long id, String nome, Double quantitàAssoluta) {
        this.id = id;
        this.nome = nome;
        this.quantitàAssoluta = quantitàAssoluta;
    }

    public Ingrediente(String nome) {
        this.nome = nome;
        this.quantitàAssoluta = 0.0;
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

    public Double getQuantitàAssoluta() {
        return quantitàAssoluta;
    }
    public String getQuantitàAssolutaToString(){
        return Double.toString(quantitàAssoluta);
    }

    public void setQuantitàAssoluta(Double quantitàAssoluta) {
        this.quantitàAssoluta = quantitàAssoluta;
    }
}