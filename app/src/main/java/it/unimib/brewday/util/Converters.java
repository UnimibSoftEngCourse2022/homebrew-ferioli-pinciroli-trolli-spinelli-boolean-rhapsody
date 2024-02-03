package it.unimib.brewday.util;

import androidx.room.TypeConverter;

import it.unimib.brewday.model.TipoIngrediente;

public class Converters {

    @TypeConverter
    public static TipoIngrediente fromString(String value) {
        for (TipoIngrediente tipo : TipoIngrediente.values()) {
            if (tipo.getNome().equals(value)) {
                return tipo;
            }
        }
        return null;
    }

    @TypeConverter
    public static String toString(TipoIngrediente tipo) {
        return tipo.getNome();
    }



}
