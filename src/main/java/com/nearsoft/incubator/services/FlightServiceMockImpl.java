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
@Component(value="flightServiceMockImpl")
public class FlightServiceMockImpl implements FlightService {
    @Override
    public List<Airport> getAllAirports() {
        Airport chihuahua = new Airport();
        chihuahua.setCity("Chihuahua");
        chihuahua.setCountryName("Mexico");
        chihuahua.setIata("CUU");
        chihuahua.setName("Roberto Fierro Airport");
        Airport mexico = new Airport();
        mexico.setCity("Distrito Federal");
        mexico.setCountryName("Mexico");
        mexico.setIata("MEX");
        mexico.setName("Benito Juarez Airport");
        Airport hermosillo = new Airport();
        hermosillo.setCity("Hermosillo");
        hermosillo.setCountryName("Mexico");
        hermosillo.setIata("HMO");
        hermosillo.setName("Ignacio Pesqueira Airport");
        LinkedList<Airport> list = new LinkedList<Airport>();
        list.add(chihuahua);
        list.add(mexico);
        list.add(hermosillo);
        return list;
    }

    @Override
    public List<Flight> getFlightsByRoute(String fromAirport, String toAirport, Date leavingDate, Date returnDate) {
        List<Flight> flights = new LinkedList<Flight>();
        Flight toMexico = new Flight();
        toMexico.setArrivalTerminal("2");
        toMexico.setArrivalDate(new Date());
        toMexico.setCarrierFsCode("AA");
        toMexico.setDepartureDate(new Date());
        toMexico.setFlightNumber("200");
        toMexico.setStops(2);
        Flight otherToMexico = new Flight();
        otherToMexico.setArrivalTerminal("1");
        otherToMexico.setArrivalDate(new Date());
        otherToMexico.setCarrierFsCode("VO");
        otherToMexico.setDepartureDate(new Date());
        otherToMexico.setFlightNumber("312");
        otherToMexico.setStops(3);
        flights.add(toMexico);
        flights.add(otherToMexico);
        return flights;
    }
}
