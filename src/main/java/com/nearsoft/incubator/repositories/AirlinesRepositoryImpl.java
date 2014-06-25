package com.nearsoft.incubator.repositories;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.dao.AirlinesDao;
import com.nearsoft.incubator.services.FlightService;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by edgar on 24/06/14.
 */
@Component("airlinesRepositoryImpl")
public class AirlinesRepositoryImpl implements AirlinesRepository {

    @Autowired
    @Qualifier("flightServiceImpl")
    private FlightService service;
    @Autowired
    @Qualifier("jdbcAirlinesDao")
    private AirlinesDao airlinesDao;
    //Time (in seconds) allowed to use results from the database before updating it with data from the service
    private long cacheExpiry;

    @Override
    public Map<String, Airline> getAirlinesMap() {
        Map<String, Airline> airlines = airlinesDao.getAirlinesMap();
        if(airlines.isEmpty() || isDataTooOld(airlines)){
            airlines = service.getAirlinesMap();
            airlinesDao.deleteAll();
            airlinesDao.save(airlines);
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
