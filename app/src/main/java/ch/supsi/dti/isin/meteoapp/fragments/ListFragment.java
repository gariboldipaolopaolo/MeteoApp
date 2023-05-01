package ch.supsi.dti.isin.meteoapp.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.activities.DetailActivity;
import ch.supsi.dti.isin.meteoapp.activities.MainActivity;
import ch.supsi.dti.isin.meteoapp.api.WeatherApiManager;
import ch.supsi.dti.isin.meteoapp.dialogs.AddCityDialog;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.LocationViewModel;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class ListFragment extends Fragment{
    private LocationViewModel locationViewModel;
    private static final String TAG = "List_Fragment";
    LocationAdapter mAdapter = new LocationAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView mLocationRecyclerView = view.findViewById(R.id.recycler_view);
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLocationRecyclerView.setAdapter(mAdapter);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory
                .getInstance(((FragmentActivity) getContext())
                        .getApplication());

        locationViewModel = new ViewModelProvider((FragmentActivity) getContext(), factory).get(LocationViewModel.class);
        locationViewModel.getAllLocations().observe(getActivity(), new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                //update RecyclerView
                mAdapter.setLocations(locations);
            }
        });

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission not granted");
            requestPermissions();
        } else {
            Log.i(TAG, "Permission granted");
            startLocationListener();
        }

        //swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                locationViewModel.delete(mAdapter.getLocationAt(viewHolder.getAdapterPosition()));

                Toast.makeText(getActivity().getApplicationContext(), R.string.location_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mLocationRecyclerView);

        mAdapter.setOnItemClickListener(new LocationAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Location location) {
                Intent intent = DetailActivity.newIntent(
                        getActivity(),
                        location.getId(),
                        location.getLatitude(),
                        location.getLongitude());

                startActivity(intent);
            }
        });
        
        return view;
    }

    // Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                openAddCityDialog();
                return true;
            case R.id.delete_all:
                locationViewModel.deleteAllLocations();
                Toast.makeText(getActivity().getApplicationContext(), R.string.all_location_deleted, Toast.LENGTH_SHORT).show();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openAddCityDialog(){
        AddCityDialog addCityDialog = new AddCityDialog();

        AddCityDialog.AddCityDialogListener listener = new AddCityDialog.AddCityDialogListener() {
            @Override
            public void applyCity(String cityName) throws IOException {
                String url = "https://api.api-ninjas.com/v1/city?name=" + cityName;

                ch.supsi.dti.isin.meteoapp.model.Location location = WeatherApiManager.getLocation(url);
                if (location.getName() != null) {
                    locationViewModel.insert(location);
                }

                String message = location.getName() != null ? getString(R.string.location_added) : getString(R.string.location_not_found);

                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        message,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        addCityDialog.setListener(listener);
        addCityDialog.show(getActivity().getSupportFragmentManager(), "add city dialog");
    }

    private void startLocationListener() {
        long mLocTrackingInterval = 1000 * 5 * 60; // 5 min
        float trackingDistance = 0;
        LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;

        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(trackingAccuracy)
                .setDistance(trackingDistance)
                .setInterval(mLocTrackingInterval);

        SmartLocation.with(getContext()).location().continuous().config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(android.location.Location location) {
                      List<Location> locations = mAdapter.getLocations();
                      if(locations.get(0).getName().contains("My position")){
                          locations.remove(0);
                      }
                      locations.add(0, new Location("My position", location.getLatitude(), location.getLongitude()));
                      mAdapter.setLocations(locations);
                    }});
    }

    private void requestPermissions() {
        while (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        startLocationListener();
    }
}
