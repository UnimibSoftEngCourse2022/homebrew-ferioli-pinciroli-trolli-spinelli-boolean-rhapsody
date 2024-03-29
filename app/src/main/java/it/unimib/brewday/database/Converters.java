package it.unimib.brewday.database;

import androidx.room.TypeConverter;

import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.model.TipoAttrezzo;

public class Converters {

    private Converters() {
        throw new IllegalStateException("Utility class");
    }

    @TypeConverter
    public static TipoIngrediente stringToIngrediente(String value) {
        for (TipoIngrediente tipo : TipoIngrediente.values()) {
            if (tipo.getNome().equals(value)) {
                return tipo;
            }
        }
        return null;
    }

    @TypeConverter
    public static TipoAttrezzo stringToAttrezzo(String value) {
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
