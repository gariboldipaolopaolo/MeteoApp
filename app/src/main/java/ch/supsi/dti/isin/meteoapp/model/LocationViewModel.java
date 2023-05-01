package ch.supsi.dti.isin.meteoapp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ch.supsi.dti.isin.meteoapp.repositories.LocationRepository;

public class LocationViewModel extends AndroidViewModel {
    private LocationRepository repository;
    private LiveData<List<Location>> allLocations;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepository(application);
        LiveData<List<Location>> remoteLocations = repository.getAllLocations();
        allLocations = remoteLocations;
    }

    public void insert(Location location){
        repository.insert(location);
    }

    public void delete(Location location){
        repository.delete(location);
    }

    public void deleteAllLocations(){
        repository.deleteAll();
    }

    public LiveData<List<Location>> getAllLocations() {
        return allLocations;
    }
}
