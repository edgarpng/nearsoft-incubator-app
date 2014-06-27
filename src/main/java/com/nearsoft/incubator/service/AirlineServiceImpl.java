package com.nearsoft.incubator.service;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.dao.AirlineDao;
import com.nearsoft.incubator.rest.client.FlightStatsClient;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by edgar on 24/06/14.
 */
@Component("airlineServiceImpl")
public class AirlineServiceImpl implements AirlineService {

    @Autowired
    @Qualifier("flightStatsClientImpl")
    private FlightStatsClient apiClient;
    @Autowired
    @Qualifier("airlineDaoHibernateImpl")
    private AirlineDao airlineDao;
    //Time (in seconds) allowed to use results from the database before updating it with data from the apiClient
    private long cacheExpiry;

    @Override
    @Transactional
    public Map<String, Airline> getAirlinesMap() {
        Map<String, Airline> airlines = airlineDao.getAirlinesMap();
        if(airlines.isEmpty() || isDataTooOld(airlines)){
            airlines = apiClient.getAirlinesMap();
            airlineDao.deleteAll();
            airlineDao.save(airlines);
        }
        return airlines;
    }

    public void setCacheExpiry(long cacheExpiry) {
        this.cacheExpiry = cacheExpiry;
    }

    private boolean isDataTooOld(Map<String, Airline> airlines){
        Airline firstAirline = airlines.values().iterator().next();
        final DateTime NOW = DateTime.now();
        DateTime airlinesCreation = new DateTime(firstAirline.getCreationDate());
        return Seconds.secondsBetween(airlinesCreation, NOW).getSeconds() > cacheExpiry;
    }
}