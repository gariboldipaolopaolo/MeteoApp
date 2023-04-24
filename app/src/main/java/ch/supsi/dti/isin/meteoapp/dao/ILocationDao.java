package ch.supsi.dti.isin.meteoapp.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ch.supsi.dti.isin.meteoapp.model.Location;

@Dao
public interface ILocationDao {
    @Insert
    void insert(Location location);

    @Query("SELECT * FROM location Order by Id Desc")
    LiveData<List<Location>> getAllLocations();

    @Delete
    void deleteLocation(Location location);

    @Query("DELETE FROM location")
    void deleteAll();
}
