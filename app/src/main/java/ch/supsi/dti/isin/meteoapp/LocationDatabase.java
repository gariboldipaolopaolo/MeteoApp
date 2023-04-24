package ch.supsi.dti.isin.meteoapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ch.supsi.dti.isin.meteoapp.dao.ILocationDao;
import ch.supsi.dti.isin.meteoapp.model.Location;

@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "locations-db";
    private static LocationDatabase sInstance;

    public static synchronized LocationDatabase getInstance(Context context){
        if(sInstance == null){
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), LocationDatabase.class, LocationDatabase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return sInstance;
    }

    public abstract ILocationDao locationDao();
}
