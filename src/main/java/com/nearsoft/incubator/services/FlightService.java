package com.nearsoft.incubator.services;

import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.bo.Schedule;

import java.util.Date;
import java.util.List;

/**
 * Created by edgar on 5/06/14.
 */
public interface FlightService {

    public List<Airport> getAllAirports();
    public Schedule getScheduleByRoute(String departureAirport, String arrivalAirport, Date departure, Date arrival);
}
