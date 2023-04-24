package ch.supsi.dti.isin.meteoapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ch.supsi.dti.isin.meteoapp.LocationDatabase;
import ch.supsi.dti.isin.meteoapp.dao.ILocationDao;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class LocationRepository {
    private ILocationDao locationDao;
    private LiveData<List<Location>> allLocations;

    public LocationRepository(Application application){
        LocationDatabase db = LocationDatabase.getInstance(application);
        locationDao = db.locationDao();
        allLocations = locationDao.getAllLocations();
    }

    public void insert(Location location){
        new InsertLocationAsync(locationDao).execute(location);
    }

    public void delete(Location location){
        new DeleteLocationAsync(locationDao).execute(location);
    }

    public void deleteAll(){
        new DeleteAllLocationAsync(locationDao).execute();
    }

    public LiveData<List<Location>> getAllLocations(){
        return allLocations;
    }

    private static class InsertLocationAsync extends AsyncTask<Location, Void, Void> {
        private ILocationDao locationDao;

        private InsertLocationAsync(ILocationDao locationDao){
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations){
            locationDao.insert(locations[0]);
            return null;
        }
    }

    private static class DeleteLocationAsync extends AsyncTask<Location, Void, Void> {
        private ILocationDao locationDao;

        private DeleteLocationAsync(ILocationDao locationDao){
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations){
            locationDao.deleteLocation(locations[0]);
            return null;
        }
    }

    private static class DeleteAllLocationAsync extends AsyncTask<Void, Void, Void> {
        private ILocationDao locationDao;

        private DeleteAllLocationAsync(ILocationDao locationDao){
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            locationDao.deleteAll();
            return null;
        }
    }
}
