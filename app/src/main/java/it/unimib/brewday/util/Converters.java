package it.unimib.brewday.util;

import androidx.room.TypeConverter;

import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.model.TipoAttrezzo;

public class Converters {

    private Converters() {
        throw new IllegalStateException("Utility class");
    }

    @TypeConverter
    public static TipoIngrediente fromString(String value) {
        for (TipoIngrediente tipo : TipoIngrediente.values()) {
            if (tipo.getNome().equals(value)) {
                return tipo;
            }
        }
    }

    @TypeConverter
    public static TipoAttrezzo fromString(String value) {
        for (TipoAttrezzo tipo : TipoAttrezzo.values()) {
            if (tipo.getNome().equals(value)) {
                return tipo;
            }
        }
        return null;
    }

    @TypeConverter
    public static String toString(TipoAttrezzo tipo) {
        return tipo.getNome();
    }

    @TypeConverter
    public static String toString(TipoIngrediente tipo) {
        return tipo.getNome();
    }



}
