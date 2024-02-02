package it.unimib.brewday.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Attrezzo {

    @PrimaryKey(autoGenerate = true) public long id;
    @ColumnInfo (name = "nome") public String nome;
    @ColumnInfo (name = "tipoAttrezzo") public TipoAttrezzo tipoAttrezzo;
    @ColumnInfo (name = "capacita") public Double capacita;

    public Attrezzo() {}

    public Attrezzo(String nome, TipoAttrezzo tipoAttrezzo, Double capacita) {
        this.nome = nome;
        this.tipoAttrezzo = tipoAttrezzo;
        this.capacita = capacita;
    }
}

