package ch.supsi.dti.isin.meteoapp.model;

import android.content.Context;
import android.util.Log;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.LocationDatabase;
import ch.supsi.dti.isin.meteoapp.R;

public class LocationsHolder {

    private static final String TAG = "Location_holder";

    private static LocationsHolder sLocationsHolder;
    private List<Location> mLocations;

    public static LocationsHolder get(LocationDatabase db) {
        if (sLocationsHolder == null)
            sLocationsHolder = new LocationsHolder(db);

        return sLocationsHolder;
    }

    private LocationsHolder(LocationDatabase db) {
        mLocations = db.locationDao().getAllLocations();
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    public Location getLocation(int id) {
        for (Location location : mLocations) {
            if (location.getId() == id)
                return location;
        }

        return null;
    }

}


