package com.example.brewday.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.brewday.model.Attrezzo;
import com.example.brewday.util.Converters;

@Database(entities = {Attrezzo.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class LocalDatabase extends RoomDatabase {
    public abstract AttrezzoDao attrezzoDao();
}
