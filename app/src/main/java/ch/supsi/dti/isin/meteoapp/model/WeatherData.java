package ch.supsi.dti.isin.meteoapp.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class WeatherData implements Serializable {
    private List<Weather> weather;
    private Main main;
    private int visibility;
    private Clouds clouds;
    private Wind wind;
    private String name;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public static class Clouds {
        private double all;

        public double getAll() {
            return all;
        }

        @NonNull
        @Override
        public String toString() {
            return "Clouds{" +
                    "all=" + all +
                    '}';
        }
    }

        public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        @NonNull
        @Override
        public String toString() {
            return "Weather{" +
                    "id=" + id +
                    ", main='" + main + '\'' +
                    ", description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    public static class Main {
        private double feels_like;
        private int pressure;
        private int humidity;

        public double getFeels_like() {
            return feels_like;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        @NonNull
        @Override
        public String toString() {
            return "Main{" +
                    ", feels_like=" + feels_like +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    '}';
        }
    }
    public static class Wind {
        private double speed;
        public double getSpeed() {
            return speed;
        }
        @NonNull
        @Override
        public String toString() {
            return "Wind{" +
                    "speed=" + speed +
                    '}';
        }
    }
    @NonNull
    @Override
    public String toString() {
        return "WeatherData{" +
                ", weather=" + weather +
                ", main=" + main +
                ", visibility=" + visibility +
                ", name='" + name +
                ", wind="+ clouds +  '\'' +
                '}';
    }
}
