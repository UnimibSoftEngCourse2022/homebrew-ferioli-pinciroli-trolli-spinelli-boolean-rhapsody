package it.unimib.brewday.util;

import androidx.room.TypeConverter;
import it.unimib.brewday.model.TipoAttrezzo;

public class Converters {

    private Converters(){}

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
}
