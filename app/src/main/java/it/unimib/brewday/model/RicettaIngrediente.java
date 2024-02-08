package it.unimib.brewday.model;

import androidx.room.Entity;

@Entity(primaryKeys = {"idRicetta", "tipoIngrediente"})
public class RicettaIngrediente {

    private long idRicetta;
    private TipoIngrediente tipoIngrediente;

}
