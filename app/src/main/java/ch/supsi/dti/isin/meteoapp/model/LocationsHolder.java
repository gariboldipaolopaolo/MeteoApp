package ch.supsi.dti.isin.meteoapp.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;


import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class LocationsHolder {

    private static final String TAG = "Location_holder";

    private static LocationsHolder sLocationsHolder;
    private List<Location> mLocations;

    public static LocationsHolder get(Context context) {
        if (sLocationsHolder == null)
            sLocationsHolder = new LocationsHolder(context);

        return sLocationsHolder;
    }

    private LocationsHolder(Context context) {
        Log.i(TAG, ""+context);
        mLocations = new ArrayList<>();
        Location location = new Location();
        //TODO gestire la parte della popolazione delle location tra salvate e la prima deve essere quella della posizione corrente
        location.setLatitude(46.5386);
        location.setLongitude(10.1357);
        location.setName("Mendrisio");
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    public Location getLocation(UUID id) {
        for (Location location : mLocations) {
            if (location.getId().equals(id))
                return location;
        }

        return null;
    }

}


