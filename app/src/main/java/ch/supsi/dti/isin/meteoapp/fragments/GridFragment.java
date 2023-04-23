package ch.supsi.dti.isin.meteoapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.WeatherData;

public class GridFragment extends Fragment {

    public static GridFragment newInstance(WeatherData weatherData) {
        Bundle args = new Bundle();
        args.putDouble("feels_like", weatherData.getMain().getFeels_like());
        args.putDouble("humidity", weatherData.getMain().getHumidity());
        args.putDouble("windSpeed", weatherData.getWind().getSpeed());
        args.putDouble("clouds", weatherData.getClouds().getAll());
        args.putDouble("visibility", weatherData.getVisibility());
        args.putDouble("airPressure", weatherData.getMain().getPressure());

        GridFragment fragment = new GridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grid_detail, container, false);

        TextView feelsLikeTextView = v.findViewById(R.id.feels_like);
        assert getArguments() != null;
        feelsLikeTextView.setText(getArguments().getDouble("feels_like") + "°C");

        TextView humidityTextView = v.findViewById(R.id.humidity);
        humidityTextView.setText(getArguments().getDouble("humidity") + "%");

        TextView windSpeedTextView = v.findViewById(R.id.wind_speed);
        windSpeedTextView.setText(getArguments().getDouble("windSpeed") + " km/h");

        TextView cloudsTextView = v.findViewById(R.id.clouds);
        cloudsTextView.setText(getArguments().getDouble("clouds") + "%");

        TextView visibilityTextView = v.findViewById(R.id.visibility);
        visibilityTextView.setText(getArguments().getDouble("visibility") / 1000 + " km");

        TextView airPressureTextView = v.findViewById(R.id.air_pressure);
        airPressureTextView.setText(getArguments().getDouble("airPressure") + " hPa");


        return v;
    }

}
