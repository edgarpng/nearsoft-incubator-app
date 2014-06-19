package com.nearsoft.incubator.services;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.bo.Flight;
import com.nearsoft.incubator.bo.Schedule;
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
    private FlightApiConfiguration configuration;

    @Override
    @Cacheable("airports")
    public List<Airport> getAllAirports() {
        Map<String, String> parameters = getCommonApiParameters();
        return restTemplate.getForObject(configuration.getAirportsUrl(), AirportsResponse.class, parameters).getAirports();
    }

    @Override
    @Cacheable("airlines")
    public Map<String, Airline> getAirlinesMap() {
        Map<String, String> parameters = getCommonApiParameters();
        List<Airline> airlines = restTemplate.getForObject(configuration.getAirlinesUrl(), AirlinesResponse.class, parameters).getAirlines();
        Map<String, Airline> airlinesMap = new HashMap<>(airlines.size());
        for(Airline airline : airlines){
            airlinesMap.put(airline.getFs(), airline);
        }
        return airlinesMap;
    }

    @Override
    public Schedule getScheduleByRoute(String departureAirport, String arrivalAirport, Date departure, Date arrival) {
        List<Flight> departureFlights = getDepartureFlights(departureAirport, arrivalAirport, departure);
        List<Flight> arrivalFlights = getArrivalFlights(arrivalAirport, departureAirport, arrival);
        Schedule schedule = new Schedule();
        schedule.setDepartureFlights(departureFlights);
        schedule.setArrivalFlights(arrivalFlights);
        return schedule;
    }

    private List<Flight> getDepartureFlights(String fromAirport, String toAirport, Date departure){
        String departuresEndpoint = configuration.getDepartingFlightsUrl();
        return callScheduleApi(departuresEndpoint, fromAirport, toAirport, departure);
    }

    private List<Flight> getArrivalFlights(String fromAirport, String toAirport, Date arrival){
        String arrivalsEndpoint = configuration.getArrivingFlightsUrl();
        return callScheduleApi(arrivalsEndpoint, fromAirport, toAirport, arrival);
    }

    private List<Flight> callScheduleApi(String url, String fromAirport, String toAirport, Date date){
        Map<String, String> parameters = getCommonApiParameters();
        parameters.put("departureAirport", fromAirport);
        parameters.put("arrivalAirport", toAirport);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        parameters.put("year", calendar.get(Calendar.YEAR) + "");
        parameters.put("month", calendar.get(Calendar.MONTH) + 1 + "");//Calendar month is zero-based (wtf?)
        parameters.put("day", calendar.get(Calendar.DAY_OF_MONTH) + "");
        return restTemplate.getForObject(url, FlightsResponse.class, parameters).getScheduledFlights();
    }

    private Map<String, String> getCommonApiParameters(){
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appId", configuration.getAppId());
        parameters.put("appKey", configuration.getAppKey());
        return parameters;
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
     * Wrapper for the Airlines API responses
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class AirlinesResponse{

        private List<Airline> airlines;

        public List<Airline> getAirlines() {
            return airlines;
        }

        public void setAirlines(List<Airline> airlines) {
            this.airlines = airlines;
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
