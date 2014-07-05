package com.nearsoft.incubator.service;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.bo.Flight;
import com.nearsoft.incubator.bo.Schedule;
import com.nearsoft.incubator.rest.client.FlightStatsClient;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by edgar on 3/07/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/service-beans-test.xml"
})
public class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private FlightStatsClient apiClient;
    @Autowired
    private AirlineService airlineService;
    private static final String DEPARTURE_AIRPORT = "SFO";
    private static final String ARRIVAL_AIRPORT = "LAX";
    private static final Date DEPARTURE_DATE = new Date();
    private static final Date ARRIVAL_DATE = new Date();

    @After
    public void resetMocks(){
        reset(apiClient);
        reset(airlineService);
    }

    @Test
    public void serviceReturnsApiResult(){
        Schedule theScheduleReturnedByTheApi = stubApiClientResult();
        expect(apiClient.getScheduleByRoute(eq(DEPARTURE_AIRPORT), eq(ARRIVAL_AIRPORT),
                eq(DEPARTURE_DATE), eq(ARRIVAL_DATE))).andReturn(theScheduleReturnedByTheApi);
        expect(airlineService.getAirlinesMap()).andReturn(stubAirlineMap());
        replay(apiClient);
        replay(airlineService);
        Schedule theScheduleReturnedByTheService = scheduleService.getScheduleByRoute(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT,
                DEPARTURE_DATE, ARRIVAL_DATE);
        assertThat(theScheduleReturnedByTheService, is(theScheduleReturnedByTheApi));
    }

    @Test
    public void serviceResolvesCorrectAirlineForEachFlight(){
        expect(apiClient.getScheduleByRoute(eq(DEPARTURE_AIRPORT), eq(ARRIVAL_AIRPORT),
                eq(DEPARTURE_DATE), eq(ARRIVAL_DATE))).andReturn(stubApiClientResult());
        expect(airlineService.getAirlinesMap()).andReturn(stubAirlineMap());
        replay(apiClient);
        replay(airlineService);
        Schedule theScheduleReturnedByTheService = scheduleService.getScheduleByRoute(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT,
                DEPARTURE_DATE, ARRIVAL_DATE);
        List<Flight> allFlightsReturnedByTheService = getAllFlightsIn(theScheduleReturnedByTheService);
        for(Flight eachFlightReturnedByTheService : allFlightsReturnedByTheService){
            Airline airlineResolvedByTheService = eachFlightReturnedByTheService.getAirline();
            assertThat(eachFlightReturnedByTheService.getAirlineFlightStatsId(),
                    equalTo(airlineResolvedByTheService.getFlightStatsId()));
        }
    }

    private List<Flight> getAllFlightsIn(Schedule schedule) {
        List<Flight> flights = new LinkedList<>();
        flights.addAll(schedule.getDepartureFlights());
        flights.addAll(schedule.getArrivalFlights());
        return flights;
    }

    private Schedule stubApiClientResult(){
        Schedule schedule = new Schedule();
        schedule.setArrivalFlights(stubFlightsForEachAirline());
        schedule.setDepartureFlights(stubFlightsForEachAirline());
        return schedule;
    }

    private List<Flight> stubFlightsForEachAirline(){
        List<Flight> flights = new LinkedList<>();
        for(Airline airline : stubAirlineList()){
            Flight flight = new Flight();
            flight.setAirlineFlightStatsId(airline.getFlightStatsId());
            flights.add(flight);
        }
        return flights;
    }

    private Map<String, Airline> stubAirlineMap(){
        Map<String, Airline> airlines = new HashMap<>();
        for(Airline airline : stubAirlineList()){
            airlines.put(airline.getFlightStatsId(), airline);
        }
        return airlines;
    }

    private List<Airline> stubAirlineList(){
        List<Airline> airlines = new LinkedList<>();
        Airline airline1 = new Airline();
        airline1.setFlightStatsId("AAA");
        Airline airline2 = new Airline();
        airline2.setFlightStatsId("BBB");
        airlines.add(airline1);
        airlines.add(airline2);
        return airlines;
    }
}
