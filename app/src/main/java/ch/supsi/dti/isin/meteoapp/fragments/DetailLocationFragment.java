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

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.WeatherData;

public class DetailLocationFragment extends Fragment {
    private static final String ARG_LOCATION_ID = "location_id";

    public static DetailLocationFragment newInstance(int locationId, WeatherData weatherData) {
        Bundle args = new Bundle();
        String weather_condition_string = weatherData.getWeather().get(0).getDescription();
        args.putSerializable(ARG_LOCATION_ID, locationId);
        args.putString("title", weatherData.getName());
        args.putString("label", weatherData.getWeather().get(0).getDescription());
        args.putString("weather_condition", weather_condition_string.substring(0, 1).toUpperCase() + weather_condition_string.substring(1));
        DetailLocationFragment fragment = new DetailLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_location, container, false);

        TextView locationNameTextView = v.findViewById(R.id.location_name);
        assert getArguments() != null;
        String name = (getArguments().getString("title").isEmpty()) ? getString(R.string.location_not_provide) : getArguments().getString("title");
        locationNameTextView.setText(name);

        TextView temperatureTextView = v.findViewById(R.id.date_text_view);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM EEE");
        String currentDate = dateFormat.format(new Date());
        temperatureTextView.setText(currentDate);

        TextView weatherConditionTextView = v.findViewById(R.id.weather_condition);
        weatherConditionTextView.setText(getArguments().getString("weather_condition"));

        ImageView imageView = v.findViewById(R.id.my_image_view);

        String weather = getArguments().getString("label");
        if (weather.contains("clouds") || weather.contains("drizzle")) {
            imageView.setBackgroundResource(R.drawable.clouds_image);
        } else if (weather.contains("rain")) {
            imageView.setBackgroundResource(R.drawable.rain_image);
        } else if (weather.contains("thunderstorm")) {
            imageView.setBackgroundResource(R.drawable.thunderstorm_image);
        } else if (weather.contains("snow")) {
            imageView.setBackgroundResource(R.drawable.snow_image);
        } else {
            imageView.setBackgroundResource(R.drawable.default_image);
        }

        return v;
    }
}

