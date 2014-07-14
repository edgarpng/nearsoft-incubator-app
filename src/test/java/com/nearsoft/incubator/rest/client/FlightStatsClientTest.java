package com.nearsoft.incubator.rest.client;

import com.nearsoft.incubator.model.Airline;
import com.nearsoft.incubator.model.Airport;
import com.nearsoft.incubator.model.Flight;
import com.nearsoft.incubator.model.Schedule;
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
    @Autowired
    private String airportsUrl;
    @Autowired
    private String airlinesUrl;
    @Autowired
    private String departureFlightsUrl;
    @Autowired
    private String arrivalFlightsUrl;
    @Autowired
    private String departureAirport;
    @Autowired
    private String arrivalAirport;
    @Autowired
    private Date departureDate;
    @Autowired
    private Date arrivalDate;

    @After
    public void resetMocks(){
        reset(restTemplate);
        reset(configuration);
    }

    @Test
    public void clientReturnsAirportsFromRestTemplate(){
        AirportsResponse responseFromRestTemplate = stubAirportsResponse();
        List<Airport> airportsReturnedByRestTemplate = responseFromRestTemplate.getAirports();
        expect(configuration.getAirportsUrl()).andReturn(airportsUrl);
        expect(restTemplate.getForObject(eq(airportsUrl), eq(AirportsResponse.class), anyObject(Map.class)))
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
        expect(configuration.getAirlinesUrl()).andReturn(airlinesUrl);
        expect(restTemplate.getForObject(eq(airlinesUrl), eq(AirlinesResponse.class), anyObject(Map.class)))
                .andReturn(responseFromRestTemplate);
        replay(configuration);
        replay(restTemplate);
        List<Airline> airlinesReturnedByTheClient = apiClient.getAllAirlines();
        assertThat(airlinesReturnedByTheClient, is(airlinesReturnedByRestTemplate));
    }

    @Test
    public void clientReturnsScheduleWithoutSwappingResponsesFromRestTemplate(){
        FlightsResponse departureResponseFromTheClient = stubFlightsResponse();
        FlightsResponse arrivalResponseFromTheClient = stubFlightsResponse();
        List<Flight> departuresReturnedByTheTemplate = departureResponseFromTheClient.getScheduledFlights();
        List<Flight> arrivalsReturnedByTheTemplate = arrivalResponseFromTheClient.getScheduledFlights();
        expect(restTemplate.getForObject(eq(departureFlightsUrl), eq(FlightsResponse.class),
                eq(departureFlightParams()))).andReturn(departureResponseFromTheClient);
        expect(restTemplate.getForObject(eq(arrivalFlightsUrl), eq(FlightsResponse.class),
                eq(arrivalFlightParams()))).andReturn(arrivalResponseFromTheClient);
        expect(configuration.getDepartingFlightsUrl()).andReturn(departureFlightsUrl);
        expect(configuration.getArrivingFlightsUrl()).andReturn(arrivalFlightsUrl);
        replay(configuration);
        replay(restTemplate);
        Schedule scheduleReturnedByTheClient = apiClient.getScheduleByRoute(departureAirport, arrivalAirport,
                departureDate, arrivalDate);
        List<Flight> departureReturnedByTheClient = scheduleReturnedByTheClient.getDepartureFlights();
        List<Flight> arrivalsReturnedByTheClient = scheduleReturnedByTheClient.getArrivalFlights();
        assertThat(departureReturnedByTheClient, is(departuresReturnedByTheTemplate));
        assertThat(arrivalsReturnedByTheClient, is(arrivalsReturnedByTheTemplate));
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
        params.put("departureAirport", departureAirport);
        params.put("arrivalAirport", arrivalAirport);
        setDate(params, departureDate);
        return params;
    }

    private Map<String, String> arrivalFlightParams(){
        Map<String, String> params = stubApiParameters();
        //In the flight back home, you arrive to the original departure airport
        params.put("departureAirport", arrivalAirport);
        params.put("arrivalAirport", departureAirport);
        setDate(params, arrivalDate);
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
