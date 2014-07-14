package com.nearsoft.incubator.service;

import com.nearsoft.incubator.model.Airport;
import com.nearsoft.incubator.dao.Dao;
import com.nearsoft.incubator.rest.client.FlightStatsClient;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by edgar on 24/06/14.
 */
@Component("airportServiceImpl")
public class AirportServiceImpl implements AirportService {

    @Autowired
    private FlightStatsClient apiClient;
    private Dao<Airport> airportDao;
    //Time (in seconds) allowed to use results from the database before updating it with data from the service
    private long cacheExpiry;

    @Override
    @Transactional
    public List<Airport> getAllAirports() {
        List<Airport> airports = airportDao.findAll();
        if(airports.isEmpty() || isDataTooOld(airports)){
            airports = apiClient.getAllAirports();
            airportDao.deleteAll();
            airportDao.saveAll(airports);
        }
        return airports;
    }

    private boolean isDataTooOld(List<Airport> airports){
        Airport firstAirport = airports.get(0);
        DateTime airportsCreation = new DateTime(firstAirport.getCreationDate());
        return Seconds.secondsBetween(airportsCreation, DateTime.now()).getSeconds() > cacheExpiry;
    }

    public void setCacheExpiry(long cacheExpiry) {
        this.cacheExpiry = cacheExpiry;
    }

    public void setAirportDao(Dao<Airport> airportDao) {
        this.airportDao = airportDao;
    }

    public void setApiClient(FlightStatsClient apiClient) {
        this.apiClient = apiClient;
    }
}
