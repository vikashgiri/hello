package com.infoicon.bonjob.retrofit.model;

/**
 * Created by infoicona on 17/10/17.
 */

public class CurrentLocation {
    String currentAddress;
    double latitude;
    double longitude;
    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
