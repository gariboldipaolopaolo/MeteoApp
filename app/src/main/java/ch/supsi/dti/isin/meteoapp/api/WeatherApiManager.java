package ch.supsi.dti.isin.meteoapp.api;


import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import ch.supsi.dti.isin.meteoapp.model.WeatherData;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;

public class WeatherApiManager {
    private static final String API_KEY = "cc76872429cb738c57ebaf64163a7534";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public interface WeatherApiCallback {
        void onSuccess(WeatherData weatherData);
        void onFailure(String errorMessage);
    }

    public static void getWeatherData(double latitude, double longitude, WeatherApiCallback callback) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder();
        urlBuilder.addQueryParameter("lat", String.valueOf(latitude));
        urlBuilder.addQueryParameter("lon", String.valueOf(longitude));
        urlBuilder.addQueryParameter("appid", API_KEY);
        urlBuilder.addQueryParameter("units","metric");
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseString = response.body().string();
                    Gson gson = new Gson();
                    WeatherData weatherData = gson.fromJson(responseString, WeatherData.class);
                    callback.onSuccess(weatherData);
                } else {
                    callback.onFailure(response.message());
                }
            }
        });
    }

    public static synchronized WeatherData getWeatherDataWithId(double latitude, double longitude) {
        WeatherData finalWeatherData = new WeatherData();
        Object lock = new Object();
        try {
            getWeatherData(latitude, longitude, new WeatherApiCallback() {
                @Override
                public void onSuccess(WeatherData data) {
                    finalWeatherData.setMain(data.getMain());
                    finalWeatherData.setWeather(data.getWeather());
                    finalWeatherData.setName(data.getName());
                    finalWeatherData.setWind(data.getWind());
                    finalWeatherData.setClouds(data.getClouds());
                    finalWeatherData.setVisibility(data.getVisibility());
                    synchronized (lock) {
                        lock.notifyAll(); // notify waiting threads
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    synchronized (lock) {
                        lock.notifyAll(); // notify waiting threads
                    }
                }
            });
            synchronized (lock) {
                lock.wait(); // wait for response
            }
        } catch (Exception ignored) {}
        return finalWeatherData;
    }}