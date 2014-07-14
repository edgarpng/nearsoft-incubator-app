package com.nearsoft.incubator.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by edgar on 5/06/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight {

    //Timestamps are kept as String to avoid unnecessary parsing
    private String departureTime;
    private String arrivalTime;
    @JsonProperty("departureAirportFsCode")
    private String departureAirportIataCode;
    @JsonProperty("arrivalAirportFsCode")
    private String arrivalAirportIataCode;
    @JsonProperty("carrierFsCode")
    private String airlineFlightStatsId;
    private String arrivalTerminal;
    private String flightNumber;
    private Airline airline;
    private int stops;

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureAirportIataCode() {
        return departureAirportIataCode;
    }

    public void setDepartureAirportIataCode(String departureAirportIataCode) {
        this.departureAirportIataCode = departureAirportIataCode;
    }

    public String getArrivalAirportIataCode() {
        return arrivalAirportIataCode;
    }

    public void setArrivalAirportIataCode(String arrivalAirportIataCode) {
        this.arrivalAirportIataCode = arrivalAirportIataCode;
    }

    public String getAirlineFlightStatsId() {
        return airlineFlightStatsId;
    }

    public void setAirlineFlightStatsId(String airlineFlightStatsId) {
        this.airlineFlightStatsId = airlineFlightStatsId;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public void setArrivalTerminal(String arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
