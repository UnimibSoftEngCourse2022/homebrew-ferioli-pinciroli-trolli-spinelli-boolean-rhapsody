package it.unimib.brewday.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Attrezzo {

    @PrimaryKey(autoGenerate = true) private long id;
    @ColumnInfo (name = "nome") private String nome;
    @ColumnInfo (name = "tipoAttrezzo") private TipoAttrezzo tipoAttrezzo;
    @ColumnInfo (name = "capacita") private Double capacita;

    public Attrezzo() {}

    public Attrezzo(String nome, TipoAttrezzo tipoAttrezzo, Double capacita) {
        this.nome = nome;
        this.tipoAttrezzo = tipoAttrezzo;
        this.capacita = capacita;
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

    public TipoAttrezzo getTipoAttrezzo() {
        return tipoAttrezzo;
    }

    public void setTipoAttrezzo(TipoAttrezzo tipoAttrezzo) {
        this.tipoAttrezzo = tipoAttrezzo;
    }

    public Double getCapacita() {
        return capacita;
    }

    public void setCapacita(Double capacita) {
        this.capacita = capacita;
    }
}

