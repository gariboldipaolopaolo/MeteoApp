package ch.supsi.dti.isin.meteoapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.dialogs.AddCityDialog;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.LocationViewModel;

public class ListFragment extends Fragment{
    private LocationViewModel locationViewModel;

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

        LocationAdapter mAdapter = new LocationAdapter();
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

        //swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                locationViewModel.delete(mAdapter.getLocationAt(viewHolder.getAdapterPosition()));

                Toast.makeText(getActivity().getApplicationContext(), "Location deleted!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mLocationRecyclerView);
        
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
                Toast.makeText(getActivity().getApplicationContext(), "All locations deleted!", Toast.LENGTH_SHORT).show();
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

                //ch.supsi.dti.isin.meteoapp.model.Location location = WeatherApiManager.getLocation(url);
                Location location = new Location("test nuovo", 10, 10);
                if (location.getName() != null) {
                    locationViewModel.insert(location);
                }

                String message = location.getName() != null ? "Location added!" : "Location not found!";

                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        message,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        addCityDialog.setListener(listener);
        addCityDialog.show(getActivity().getSupportFragmentManager(), "add city dialog");
    }
}
