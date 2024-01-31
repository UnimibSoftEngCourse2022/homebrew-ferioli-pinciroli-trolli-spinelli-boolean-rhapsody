package com.example.brewday.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Attrezzo {

    @PrimaryKey public long id;
    @ColumnInfo (name = "nome") public String nome;
    @ColumnInfo (name = "tipoAttrezzo") public TipoAttrezzo tipoAttrezzo;
    @ColumnInfo (name = "capacita") public Double capacita;

    public Attrezzo() {}
}

