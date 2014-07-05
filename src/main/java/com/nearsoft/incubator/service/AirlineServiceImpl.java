package com.nearsoft.incubator.service;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.dao.Dao;
import com.nearsoft.incubator.rest.client.FlightStatsClient;
import com.nearsoft.incubator.util.Airlines;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by edgar on 24/06/14.
 */
@Component("airlineServiceImpl")
public class AirlineServiceImpl implements AirlineService {

    @Autowired
    private FlightStatsClient apiClient;
    private Dao<Airline> airlineDao;
    //Time (in seconds) allowed to use results from the database before updating it with data from the apiClient
    private long cacheExpiry;

    @Override
    @Transactional
    public Map<String, Airline> getAirlinesMap() {
        List<Airline> airlines = airlineDao.findAll();
        if(airlines.isEmpty() || isDataTooOld(airlines)){
            airlines = apiClient.getAllAirlines();
            airlineDao.deleteAll();
            airlineDao.saveAll(airlines);
        }
        return Airlines.toAirlinesMap(airlines);
    }

    private boolean isDataTooOld(List<Airline> airlines){
        Airline firstAirline = airlines.get(0);
        DateTime airlinesCreation = new DateTime(firstAirline.getCreationDate());
        return Seconds.secondsBetween(airlinesCreation, DateTime.now()).getSeconds() > cacheExpiry;
    }

    public void setCacheExpiry(long cacheExpiry) {
        this.cacheExpiry = cacheExpiry;
    }

    public void setAirlineDao(Dao<Airline> airlineDao){
        this.airlineDao = airlineDao;
    }

    public void setApiClient(FlightStatsClient apiClient){
        this.apiClient = apiClient;
    }
}
