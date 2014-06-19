package com.nearsoft.incubator.bo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by edgar on 5/06/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight {

    //Timestamps are kept as String to avoid unnecessary parsing
    private String departureTime;
    private String arrivalTime;
    private String departureAirportFsCode;
    private String arrivalAirportFsCode;
    private String carrierFsCode;
    private String arrivalTerminal;
    private String flightNumber;
    private Airline airline;
    private int stops;

    public String getId() {
        return this.flightNumber;
    }

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

    public String getDepartureAirportFsCode() {
        return departureAirportFsCode;
    }

    public void setDepartureAirportFsCode(String departureAirportFsCode) {
        this.departureAirportFsCode = departureAirportFsCode;
    }

    public String getArrivalAirportFsCode() {
        return arrivalAirportFsCode;
    }

    public void setArrivalAirportFsCode(String arrivalAirportFsCode) {
        this.arrivalAirportFsCode = arrivalAirportFsCode;
    }

    public String getCarrierFsCode() {
        return carrierFsCode;
    }

    public void setCarrierFsCode(String carrierFsCode) {
        this.carrierFsCode = carrierFsCode;
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
