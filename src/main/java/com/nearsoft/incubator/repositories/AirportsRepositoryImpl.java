package com.nearsoft.incubator.repositories;

import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.dao.AirportsDao;
import com.nearsoft.incubator.services.FlightService;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by edgar on 24/06/14.
 */
@Component("airportsRepositoryImpl")
public class AirportsRepositoryImpl implements AirportsRepository {

    @Autowired
    @Qualifier("flightServiceImpl")
    private FlightService service;
    @Autowired
    @Qualifier("jdbcAirportsDao")
    private AirportsDao airportsDao;
    //Time (in seconds) allowed to use results from the database before updating it with data from the service
    private long cacheExpiry;

    @Override
    public List<Airport> getAllAirports() {
        List<Airport> airports = airportsDao.getAllAirports();
        if(airports.isEmpty() || isDataTooOld(airports)){
            airports = service.getAllAirports();
            airportsDao.deleteAll();
            airportsDao.save(airports);
        }
        return airports;
    }

    public void setCacheExpiry(long cacheExpiry) {
        this.cacheExpiry = cacheExpiry;
    }

    private boolean isDataTooOld(List<Airport> airports){
        Airport firstAirport = airports.get(1);
        final DateTime NOW = DateTime.now();
        DateTime airportsCreation = new DateTime(firstAirport.getCreationDate());
        return Seconds.secondsBetween(airportsCreation, NOW).getSeconds() > cacheExpiry;
    }
}
