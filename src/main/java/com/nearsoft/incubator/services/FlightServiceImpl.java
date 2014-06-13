package com.nearsoft.incubator.services;

import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.bo.Flight;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by edgar on 12/06/14.
 */
@Component(value="flightServiceImpl")
public class FlightServiceImpl implements FlightService {

    @Autowired
    private RestTemplate restTemplate;
    private static final String APP_ID = "9a08398d";
    private static final String APP_KEY = "d13a463da989d36d66a3b26ecb5c2a6b";

    @Override
    public List<Airport> getAllAirports() {
        final String URL = "https://api.flightstats.com/flex/airports/rest/v1/json/active?appId={appId}&appKey={appKey}";
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appId", APP_ID);
        parameters.put("appKey", APP_KEY);
        return restTemplate.getForObject(URL, AirportsResponse.class, parameters).getAirports();
    }

    @Override
    public List<Flight> getFlightsByRoute(String fromAirport, String toAirport, Date leavingDate, Date returnDate) {
        final String URL = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/{fromAirport}/to/{toAirport}/departing/{departingYear}/{departingMonth}/{departingDay}?appId={appId}&appKey={appKey}";
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appId", APP_ID);
        parameters.put("appKey", APP_KEY);
        parameters.put("fromAirport", fromAirport);
        parameters.put("toAirport", toAirport);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(leavingDate);
        parameters.put("departingYear", calendar.get(Calendar.YEAR) + "");
        parameters.put("departingMonth", calendar.get(Calendar.MONTH) + 1 + "");//Calendar month is zero-based (wtf?)
        parameters.put("departingDay", calendar.get(Calendar.DAY_OF_MONTH) + "");
        return restTemplate.getForObject(URL, FlightsResponse.class, parameters).getScheduledFlights();
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
