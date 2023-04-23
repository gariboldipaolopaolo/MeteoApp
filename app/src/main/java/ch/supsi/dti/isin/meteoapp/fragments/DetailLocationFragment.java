package ch.supsi.dti.isin.meteoapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.WeatherData;

public class DetailLocationFragment extends Fragment {
    private static final String ARG_LOCATION_ID = "location_id";

    private Location mLocation;
    private TextView mIdTextView;

    public static DetailLocationFragment newInstance(UUID locationId, WeatherData weatherData) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCATION_ID, locationId);
        args.putString("title", weatherData.getName());
        args.putString("label", weatherData.getWeather().get(0).getDescription());
        args.putString("weather_condition", weatherData.getWeather().get(0).getMain());
        DetailLocationFragment fragment = new DetailLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        UUID locationId = (UUID) getArguments().getSerializable(ARG_LOCATION_ID);
        mLocation = LocationsHolder.get(getActivity()).getLocation(locationId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_location, container, false);

        TextView locationNameTextView = v.findViewById(R.id.location_name);
        assert getArguments() != null;
        locationNameTextView.setText(getArguments().getString("title"));

        TextView temperatureTextView = v.findViewById(R.id.date_text_view);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM EEE");
        String currentDate = dateFormat.format(new Date());
        temperatureTextView.setText(currentDate);

        TextView weatherConditionTextView = v.findViewById(R.id.weather_condition);
        weatherConditionTextView.setText(getArguments().getString("weather_condition"));

        ImageView imageView = v.findViewById(R.id.my_image_view);
        switch(getArguments().getString("label")) {
            case "clear sky":
                imageView.setBackgroundResource(R.drawable.clear_sky_image);
                break;
            case "few clouds":
                imageView.setImageResource(R.drawable.few_clouds_image);
                break;
            case "scattered clouds":
                imageView.setImageResource(R.drawable.scatter_clouds_image);
                break;
            case "broken clouds":
                imageView.setBackgroundResource(R.drawable.broken_clouds_image);
                break;
            case "shower rain":
                imageView.setBackgroundResource(R.drawable.shower_rain_image);
                break;
            case "rain":
                imageView.setBackgroundResource(R.drawable.rain_image);
                break;
            case "thunderstorm":
                imageView.setBackgroundResource(R.drawable.thunderstorm_image);
                break;
            case "snow":
                imageView.setBackgroundResource(R.drawable.snow_image);
                break;
            case "mist":
                imageView.setBackgroundResource(R.drawable.mist_image);
                break;
        }

        return v;
    }
}

