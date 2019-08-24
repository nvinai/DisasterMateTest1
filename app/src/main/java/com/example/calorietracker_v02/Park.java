package com.example.calorietracker_v02;

public class Park {
    double longitude;
    double latitude;
    String name;
    String vicinity;

    public Park(double longitude, double latitude, String name, String vicinity) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.vicinity = vicinity;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
