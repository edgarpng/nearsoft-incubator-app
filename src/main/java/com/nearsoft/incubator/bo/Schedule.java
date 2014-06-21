package com.nearsoft.incubator.bo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by edgar on 17/06/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {

    private List<Flight> departureFlights;
    private List<Flight> arrivalFlights;

    public int getId(){
        return 1;
    }

    public List<Flight> getDepartureFlights() {
        return departureFlights;
    }

    public void setDepartureFlights(List<Flight> departureFlights) {
        this.departureFlights = departureFlights;
    }

    public List<Flight> getArrivalFlights() {
        return arrivalFlights;
    }

    public void setArrivalFlights(List<Flight> returnFlights) {
        this.arrivalFlights = returnFlights;
    }
}
