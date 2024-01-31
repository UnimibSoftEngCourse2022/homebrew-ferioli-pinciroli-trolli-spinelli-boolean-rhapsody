package com.example.brewday.util;

import androidx.room.TypeConverter;
import com.example.brewday.model.TipoAttrezzo;

public class Converters {

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
