package com.nearsoft.incubator.rest.client;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.bo.Flight;
import com.nearsoft.incubator.bo.Schedule;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by edgar on 12/06/14.
 */
@Component("flightStatsClientImpl")
public class FlightStatsClientImpl implements FlightStatsClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FlightStatsClientConfiguration configuration;

    @Override
    public List<Airport> getAllAirports() {
        Map<String, String> parameters = getCommonApiParameters();
        return restTemplate.getForObject(configuration.getAirportsUrl(), AirportsResponse.class, parameters).getAirports();
    }

    @Override
    public List<Airline> getAllAirlines() {
        Map<String, String> parameters = getCommonApiParameters();
        return restTemplate.getForObject(configuration.getAirlinesUrl(), AirlinesResponse.class, parameters).getAirlines();
    }

    @Override
    public Schedule getScheduleByRoute(String departureAirportIataCode, String arrivalAirportIataCode, Date departure, Date arrival) {
        List<Flight> departureFlights = getDepartureFlights(departureAirportIataCode, arrivalAirportIataCode, departure);
        List<Flight> arrivalFlights = getArrivalFlights(arrivalAirportIataCode, departureAirportIataCode, arrival);
        Schedule schedule = new Schedule();
        schedule.setDepartureFlights(departureFlights);
        schedule.setArrivalFlights(arrivalFlights);
        return schedule;
    }

    private List<Flight> getDepartureFlights(String fromAirportIataCode, String toAirportIataCode, Date departure){
        String departuresEndpoint = configuration.getDepartingFlightsUrl();
        return callScheduleApi(departuresEndpoint, fromAirportIataCode, toAirportIataCode, departure);
    }

    private List<Flight> getArrivalFlights(String fromAirportIataCode, String toAirportIataCode, Date arrival){
        String arrivalsEndpoint = configuration.getArrivingFlightsUrl();
        return callScheduleApi(arrivalsEndpoint, fromAirportIataCode, toAirportIataCode, arrival);
    }

    private List<Flight> callScheduleApi(String url, String fromAirportIataCode, String toAirportIataCode, Date date){
        Map<String, String> parameters = getCommonApiParameters();
        parameters.put("departureAirport", fromAirportIataCode);
        parameters.put("arrivalAirport", toAirportIataCode);
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
