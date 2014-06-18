package com.nearsoft.incubator.services;

import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.bo.Flight;
import com.nearsoft.incubator.util.FlightApiConfiguration;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by edgar on 12/06/14.
 */
@Component("flightServiceImpl")
public class FlightServiceImpl implements FlightService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    @Qualifier("apiConfiguration")
    FlightApiConfiguration configuration;

    @Override
    @Cacheable("airports")
    public List<Airport> getAllAirports() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appId", configuration.getAppId());
        parameters.put("appKey", configuration.getAppKey());
        return restTemplate.getForObject(configuration.getAirportsUrl(), AirportsResponse.class, parameters).getAirports();
    }

    @Override
    public List<Flight> getFlightsByRoute(String fromAirport, String toAirport, Date leavingDate, Date returnDate) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appId", configuration.getAppId());
        parameters.put("appKey", configuration.getAppKey());
        parameters.put("fromAirport", fromAirport);
        parameters.put("toAirport", toAirport);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(leavingDate);
        parameters.put("departingYear", calendar.get(Calendar.YEAR) + "");
        parameters.put("departingMonth", calendar.get(Calendar.MONTH) + 1 + "");//Calendar month is zero-based (wtf?)
        parameters.put("departingDay", calendar.get(Calendar.DAY_OF_MONTH) + "");
        return restTemplate.getForObject(configuration.getFlightsUrl(), FlightsResponse.class, parameters).getScheduledFlights();
    }

    /**
     * Wrapper for the Airports API response
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class AirportsResponse{

        private List<Airport> airports;

        public List<Airport> getAirports(){
            return this.airports;
        }

        public void setAirports(List<Airport> airports){
            this.airports = airports;
        }
    }

    /**
     * Wrapper for the Flights' API responses
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class FlightsResponse{

        private List<Flight> scheduledFlights;

        public List<Flight> getScheduledFlights() {
            return scheduledFlights;
        }

        public void setScheduledFlights(List<Flight> scheduledFlights) {
            this.scheduledFlights = scheduledFlights;
        }
    }
}
