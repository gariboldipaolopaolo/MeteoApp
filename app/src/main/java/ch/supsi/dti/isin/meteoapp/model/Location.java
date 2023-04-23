package ch.supsi.dti.isin.meteoapp.model;

import java.util.UUID;

public class Location {
    private UUID Id;
    private String mName;
    private double mLatitude;
    private double mLongitude;
    private int degree;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public Location() {
        Id = UUID.randomUUID();
    }

    public int getDegree() { return degree; }

    public void setDegree(int deg) { degree = deg; }
}