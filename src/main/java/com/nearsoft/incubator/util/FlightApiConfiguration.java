package com.nearsoft.incubator.util;

/**
 * Created by edgar on 13/06/14.
 */
public class FlightApiConfiguration {

    private String appKey;
    private String appId;
    private String airportsUrl;
    private String flightsUrl;

    public String getAppKey() {
        return appKey;
    }

    public String getAppId() {
        return appId;
    }

    public String getAirportsUrl() {
        return airportsUrl;
    }

    public String getFlightsUrl() {
        return flightsUrl;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAirportsUrl(String airportsUrl) {
        this.airportsUrl = airportsUrl;
    }

    public void setFlightsUrl(String flightsUrl) {
        this.flightsUrl = flightsUrl;
    }
}
