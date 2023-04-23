package ch.supsi.dti.isin.meteoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.api.WeatherApiManager;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;
import ch.supsi.dti.isin.meteoapp.fragments.GridFragment;
import ch.supsi.dti.isin.meteoapp.model.WeatherData;

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";
    private static final String EXTRA_LATITUDE = "ch.supsi.dti.isin.meteoapp.latitude";
    private static final String EXTRA_LONGITUDE = "ch.supsi.dti.isin.meteoapp.longitude";

    public static Intent newIntent(Context packageContext, int locationId, double lat, double lon) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_LOCATION_ID, locationId);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lon);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_double_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {

            int locationId = (int) getIntent().getSerializableExtra(EXTRA_LOCATION_ID);
            double latitude = getIntent().getDoubleExtra(EXTRA_LATITUDE, 0);
            double longitude = getIntent().getDoubleExtra(EXTRA_LONGITUDE, 0);
            WeatherData weatherData = WeatherApiManager.getWeatherDataWithId(latitude, longitude);
            fragment = new DetailLocationFragment().newInstance(locationId, weatherData);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();

            fragment = new GridFragment().newInstance(weatherData);
            fm.beginTransaction()
                    .add(R.id.fragment_container_grid, fragment)
                    .commit();
        }
    }
}