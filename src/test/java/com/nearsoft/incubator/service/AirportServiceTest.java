package com.nearsoft.incubator.service;

import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.dao.Dao;
import com.nearsoft.incubator.rest.client.FlightStatsClient;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by edgar on 3/07/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/service-beans-test.xml"
})
public class AirportServiceTest {

    @Autowired
    private AirportService airportService;
    @Autowired
    private Dao<Airport> airportDao;
    @Autowired
    private FlightStatsClient apiClient;
    @Autowired
    private int serviceCacheExpiry;

    @After
    public void resetMocks(){
        reset(airportDao);
        reset(apiClient);
    }

    @Test
    public void serviceReturnsDaoResult(){
        List<Airport> airportsReturnedByTheDao = stubAirportList();
        expect(airportDao.findAll()).andReturn(airportsReturnedByTheDao);
        replay(airportDao);
        List<Airport> airportsReturnedByTheService = airportService.getAllAirports();
        assertThat(airportsReturnedByTheService, is(airportsReturnedByTheDao));
    }

    @Test
    public void serviceReturnsApiResultWhenDaoReturnsEmptyList(){
        List<Airport> emptyList = stubEmptyAirportList();
        List<Airport> airportsReturnedByTheApi = stubAirportList();
        expect(airportDao.findAll()).andReturn(emptyList);
        expect(apiClient.getAllAirports()).andReturn(airportsReturnedByTheApi);
        airportDao.deleteAll();
        airportDao.saveAll(eq(airportsReturnedByTheApi));
        replay(airportDao);
        replay(apiClient);
        List<Airport> airportsReturnedByTheService = airportService.getAllAirports();
        assertThat(airportsReturnedByTheService, is(airportsReturnedByTheApi));
    }

    @Test
    public void serviceReturnsApiResultWhenDaoReturnsOldData(){
        List<Airport> oldAirports = stubAirportsCreatedSecondsAgo(serviceCacheExpiry + 1);
        List<Airport> airportsReturnedByTheApi = stubAirportList();
        expect(airportDao.findAll()).andReturn(oldAirports);
        expect(apiClient.getAllAirports()).andReturn(airportsReturnedByTheApi);
        airportDao.deleteAll();
        airportDao.saveAll(eq(airportsReturnedByTheApi));
        replay(airportDao);
        replay(apiClient);
        List<Airport> airportsReturnedByTheService = airportService.getAllAirports();
        assertThat(airportsReturnedByTheService, is(airportsReturnedByTheApi));
    }

    private List<Airport> stubEmptyAirportList(){
        return new LinkedList<Airport>();
    }

    private List<Airport> stubAirportList(){
        List<Airport> Airports = new LinkedList<>();
        Airports.add(new Airport());
        return Airports;
    }

    private List<Airport> stubAirportsCreatedSecondsAgo(int seconds){
        List<Airport> airports = stubAirportList();
        for(Airport airport : airports){
            DateTime now = DateTime.now();
            airport.setCreationDate(now.minusSeconds(seconds).toDate());
        }
        return airports;
    }
}
