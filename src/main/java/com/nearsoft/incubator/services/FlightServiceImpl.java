package com.nearsoft.incubator.services;

import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.bo.Flight;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by edgar on 5/06/14.
 */
@Component(value="flightServiceImpl")
public class FlightServiceImpl implements FlightService {
    @Override
    public List<Airport> getAllAirports() {
        //TODO: Use the API
        Airport chihuahua = new Airport();
        chihuahua.setCity("Chihuahua");
        chihuahua.setCountryName("Mexico");
        chihuahua.setIata("CUU");
        chihuahua.setName("Roberto Fierro Airport");
        LinkedList<Airport> list = new LinkedList<Airport>();
        list.add(chihuahua);
        return list;
    }

    @Override
    public List<Flight> getFlightsByRoute(Airport fromAirport, Airport toAirport, Date leavingDate, Date returnDate) {
        //TODO: Use the API
        List<Flight> flights = new LinkedList<Flight>();
        Flight toMexico = new Flight();
        toMexico.setArrivalTerminal("2");
        toMexico.setArrivalTime(new Date());
        toMexico.setCarrierFsCode("AA");
        toMexico.setDepartureTime(new Date());
        toMexico.setFlightNumber("200");
        toMexico.setStops(2);
        flights.add(toMexico);
        return flights;
    }
}
