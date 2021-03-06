package com.nearsoft.incubator.service;

import com.nearsoft.incubator.model.Airline;
import com.nearsoft.incubator.model.Flight;
import com.nearsoft.incubator.model.Schedule;
import com.nearsoft.incubator.rest.client.FlightStatsClient;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FlightStatsClient apiClient;
    @Autowired
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
            Airline airline = airlinesMap.get(flight.getAirlineFlightStatsId());
            flight.setAirline(airline);
        }
    }

    public void setAirlineService(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    public void setApiClient(FlightStatsClient apiClient) {
        this.apiClient = apiClient;
    }
}
