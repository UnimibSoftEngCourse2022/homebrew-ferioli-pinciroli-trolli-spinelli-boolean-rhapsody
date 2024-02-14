package it.unimib.brewday.util;

import static android.provider.Settings.System.DATE_FORMAT;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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
