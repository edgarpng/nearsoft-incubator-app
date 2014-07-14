package com.nearsoft.incubator.service;

import com.nearsoft.incubator.model.Airline;
import com.nearsoft.incubator.dao.Dao;
import com.nearsoft.incubator.rest.client.FlightStatsClient;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Created by edgar on 2/07/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/service-beans-test.xml"
})
public class AirlineServiceTest {

    @Autowired
    private Dao<Airline> airlineDao;
    @Resource(name="airlineServiceImpl")
    private AirlineService airlineService;
    @Autowired
    private FlightStatsClient apiClient;
    @Autowired
    private int serviceCacheExpiry;

    @After
    public void resetMocks(){
        reset(airlineDao);
        reset(apiClient);
    }

    @Test
    public void serviceReturnsDaoResult(){
        List<Airline> airlinesListReturnedByTheDao = stubAirlineList();
        expect(airlineDao.findAll()).andReturn(airlinesListReturnedByTheDao);
        replay(airlineDao);
        Map<String, Airline> airlinesReturnedByTheService = airlineService.getAirlinesMap();
        assertThat(airlinesReturnedByTheService.size(), equalTo(airlinesListReturnedByTheDao.size()));
        assertThat(airlinesReturnedByTheService.values(), hasItems(inThe(airlinesListReturnedByTheDao)));
    }

    @Test
    public void serviceReturnsApiResultWhenDaoReturnsEmptyList(){
        List<Airline> emptyList = stubEmptyAirlineList();
        List<Airline> airlinesReturnedByTheApi = stubAirlineList();
        expect(airlineDao.findAll()).andReturn(emptyList);
        expect(apiClient.getAllAirlines()).andReturn(airlinesReturnedByTheApi);
        airlineDao.deleteAll();
        airlineDao.saveAll(eq(airlinesReturnedByTheApi));
        replay(airlineDao);
        replay(apiClient);
        Map<String, Airline> airlinesReturnedByTheService = airlineService.getAirlinesMap();
        assertThat(airlinesReturnedByTheService.size(), equalTo(airlinesReturnedByTheApi.size()));
        assertThat(airlinesReturnedByTheService.values(), hasItems(inThe(airlinesReturnedByTheApi)));
        verify(airlineDao);
    }

    @Test
    public void serviceReturnsApiResultWhenDaoReturnsOldData(){
        List<Airline> oldAirlines = stubAirlinesCreatedSecondsAgo(serviceCacheExpiry + 1);
        List<Airline> airlinesReturnedByTheApi = stubAirlineList();
        expect(airlineDao.findAll()).andReturn(oldAirlines);
        expect(apiClient.getAllAirlines()).andReturn(airlinesReturnedByTheApi);
        airlineDao.deleteAll();
        airlineDao.saveAll(eq(airlinesReturnedByTheApi));
        replay(airlineDao);
        replay(apiClient);
        Map<String, Airline> airlinesReturnedByTheService = airlineService.getAirlinesMap();
        assertThat(airlinesReturnedByTheService.size(), equalTo(airlinesReturnedByTheApi.size()));
        assertThat(airlinesReturnedByTheService.values(), hasItems(inThe(airlinesReturnedByTheApi)));
        verify(airlineDao);
    }

    private Airline[] inThe(List<Airline> list) {
        return list.toArray(new Airline[]{});
    }

    private List<Airline> stubEmptyAirlineList(){
        return new LinkedList<Airline>();
    }

    private List<Airline> stubAirlineList(){
        List<Airline> airlines = new LinkedList<>();
        airlines.add(new Airline());
        return airlines;
    }

    private List<Airline> stubAirlinesCreatedSecondsAgo(int seconds){
        List<Airline> airlines = stubAirlineList();
        for(Airline airline : airlines){
            DateTime now = DateTime.now();
            airline.setCreationDate(now.minusSeconds(seconds).toDate());
        }
        return airlines;
    }

    public void setAirlineService(AirlineService airlineService) {
        this.airlineService = airlineService;
    }
}
