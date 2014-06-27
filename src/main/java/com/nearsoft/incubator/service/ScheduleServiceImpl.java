package com.nearsoft.incubator.service;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.bo.Flight;
import com.nearsoft.incubator.bo.Schedule;
import com.nearsoft.incubator.rest.client.FlightStatsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar on 26/06/14.
 */
@Component("scheduleServiceImpl")
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    @Qualifier("flightStatsClientImpl")
    private FlightStatsClient apiClient;
    @Autowired
    @Qualifier("airlineServiceImpl")
    private AirlineService airlineService;

    @Override
    public Schedule getScheduleByRoute(String departureAirportIataCode, String arrivalAirportIataCode,
                                       Date departure, Date arrival) {
        Schedule schedule = apiClient.getScheduleByRoute(departureAirportIataCode, arrivalAirportIataCode, departure, arrival);
        //Add the corresponding Airline object for every flight
        Map<String, Airline> airlines = airlineService.getAirlinesMap();
        setAirlineOnFlights(schedule.getDepartureFlights(), airlines);
        setAirlineOnFlights(schedule.getArrivalFlights(), airlines);
        return schedule;
    }

    private void setAirlineOnFlights(List<Flight> flights, Map<String, Airline> airlinesMap){
        for(Flight flight: flights){
            Airline airline = airlinesMap.get(flight.getCarrierFsCode());
            flight.setAirline(airline);
        }
    }
}
