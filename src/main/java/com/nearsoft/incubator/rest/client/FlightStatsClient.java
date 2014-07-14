package com.nearsoft.incubator.rest.client;

import com.nearsoft.incubator.model.Airline;
import com.nearsoft.incubator.model.Airport;
import com.nearsoft.incubator.model.Schedule;

import java.util.Date;
import java.util.List;

/**
 * Created by edgar on 5/06/14.
 */
public interface FlightStatsClient {

    public List<Airport> getAllAirports();
    public List<Airline> getAllAirlines();
    public Schedule getScheduleByRoute(String departureAirport, String arrivalAirport, Date departure, Date arrival);
}
