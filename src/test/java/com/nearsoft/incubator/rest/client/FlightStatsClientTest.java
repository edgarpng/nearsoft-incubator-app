package com.nearsoft.incubator.rest.client;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.bo.Flight;
import com.nearsoft.incubator.bo.Schedule;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.nearsoft.incubator.rest.client.FlightStatsClientImpl.*;
import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by edgar on 4/07/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/rest-client-beans-test.xml"
})
public class FlightStatsClientTest {

    @Autowired
    private FlightStatsClient apiClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FlightStatsClientConfiguration configuration;
    private static final String AIRPORTS_URL = "a";
    private static final String AIRLINES_URL = "b";
    private static final String DEPARTURE_FLIGHTS_URL = "c";
    private static final String ARRIVAL_FLIGHTS_URL = "d";
    private static final String HOME_AIRPORT = "e";
    private static final String DESTINATION_AIRPORT = "f";
    private static final Date DATE_DEPARTING_FROM_HOME = new Date();
    private static final Date DATE_RETURNING_HOME = new Date();

    @After
    public void resetMocks(){
        reset(restTemplate);
        reset(configuration);
    }

    @Test
    public void clientReturnsAirportsFromRestTemplate(){
        AirportsResponse responseFromRestTemplate = stubAirportsResponse();
        List<Airport> airportsReturnedByRestTemplate = responseFromRestTemplate.getAirports();
        expect(configuration.getAirportsUrl()).andReturn(AIRPORTS_URL);
        expect(restTemplate.getForObject(eq(AIRPORTS_URL), eq(AirportsResponse.class), anyObject(Map.class)))
                .andReturn(responseFromRestTemplate);
        replay(configuration);
        replay(restTemplate);
        List<Airport> airportsReturnedByTheClient = apiClient.getAllAirports();
        assertThat(airportsReturnedByTheClient, is(airportsReturnedByRestTemplate));
    }

    @Test
    public void clientReturnsAirlinesFromRestTemplate(){
        AirlinesResponse responseFromRestTemplate = stubAirlinesResponse();
        List<Airline> airlinesReturnedByRestTemplate = responseFromRestTemplate.getAirlines();
        expect(configuration.getAirlinesUrl()).andReturn(AIRLINES_URL);
        expect(restTemplate.getForObject(eq(AIRLINES_URL), eq(AirlinesResponse.class), anyObject(Map.class)))
                .andReturn(responseFromRestTemplate);
        replay(configuration);
        replay(restTemplate);
        List<Airline> airlinesReturnedByTheClient = apiClient.getAllAirlines();
        assertThat(airlinesReturnedByTheClient, is(airlinesReturnedByRestTemplate));
    }

    @Test
    public void clientReturnsScheduleWithoutSwappingResponsesFromRestTemplate(){
        FlightsResponse templateResponseDeparture = stubFlightsResponse();
        FlightsResponse templateResponseArrival = stubFlightsResponse();
        List<Flight> templateDepartures = templateResponseDeparture.getScheduledFlights();
        List<Flight> templateArrivals = templateResponseArrival.getScheduledFlights();
        expect(restTemplate.getForObject(eq(DEPARTURE_FLIGHTS_URL), eq(FlightsResponse.class),
                eq(departureFlightParams()))).andReturn(templateResponseDeparture);
        expect(restTemplate.getForObject(eq(ARRIVAL_FLIGHTS_URL), eq(FlightsResponse.class),
                eq(arrivalFlightParams()))).andReturn(templateResponseArrival);
        expect(configuration.getDepartingFlightsUrl()).andReturn(DEPARTURE_FLIGHTS_URL);
        expect(configuration.getArrivingFlightsUrl()).andReturn(ARRIVAL_FLIGHTS_URL);
        replay(configuration);
        replay(restTemplate);
        Schedule clientSchedule = apiClient.getScheduleByRoute(HOME_AIRPORT, DESTINATION_AIRPORT,
                DATE_DEPARTING_FROM_HOME, DATE_RETURNING_HOME);
        List<Flight> clientDepartures = clientSchedule.getDepartureFlights();
        List<Flight> clientArrivals = clientSchedule.getArrivalFlights();
        assertThat(clientDepartures, is(templateDepartures));
        assertThat(clientArrivals, is(templateArrivals));
    }

    private Map<String, String> stubApiParameters(){
        Map<String, String> params = new HashMap<>();
        params.put("appKey", null);
        params.put("appId", null);
        return params;
    }

    private void setDate(Map<String, String> parameters, Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        parameters.put("year", calendar.get(Calendar.YEAR) + "");
        parameters.put("month", calendar.get(Calendar.MONTH) + 1 + "");
        parameters.put("day", calendar.get(Calendar.DAY_OF_MONTH) + "");
    }

    private Map<String, String> departureFlightParams(){
        Map<String, String> params = stubApiParameters();
        params.put("departureAirport", HOME_AIRPORT);
        params.put("arrivalAirport", DESTINATION_AIRPORT);
        setDate(params, DATE_DEPARTING_FROM_HOME);
        return params;
    }

    private Map<String, String> arrivalFlightParams(){
        Map<String, String> params = stubApiParameters();
        params.put("departureAirport", DESTINATION_AIRPORT);
        params.put("arrivalAirport", HOME_AIRPORT);
        setDate(params, DATE_RETURNING_HOME);
        return params;
    }

    private List<Airport> stubAirports(){
        return new LinkedList<>();
    }

    private List<Airline> stubAirlines(){
        return new LinkedList<>();
    }

    private List<Flight> stubFlights(){
        return new LinkedList<>();
    }

    private AirportsResponse stubAirportsResponse(){
        AirportsResponse response = new AirportsResponse();
        response.setAirports(stubAirports());
        return response;
    }

    private AirlinesResponse stubAirlinesResponse(){
        AirlinesResponse response = new AirlinesResponse();
        response.setAirlines(stubAirlines());
        return response;
    }

    private FlightsResponse stubFlightsResponse(){
        FlightsResponse response = new FlightsResponse();
        response.setScheduledFlights(stubFlights());
        return response;
    }
}
