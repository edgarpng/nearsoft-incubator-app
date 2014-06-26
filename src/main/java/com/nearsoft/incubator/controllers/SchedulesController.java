package com.nearsoft.incubator.controllers;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.bo.Flight;
import com.nearsoft.incubator.bo.Schedule;
import com.nearsoft.incubator.managers.AirlinesManager;
import com.nearsoft.incubator.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Component
@RequestMapping("/schedules")
public class SchedulesController extends BaseController {

    @Autowired
    @Qualifier("flightServiceImpl")
    private FlightService service;
    @Autowired
    @Qualifier("airlinesManagerImpl")
    private AirlinesManager airlinesManager;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Schedule getFlights(@RequestParam("departureAirport") String departureAirportIataCode,
                                                               @RequestParam("arrivalAirport") String arrivalAirportIataCode,
                                                               @RequestParam("departureDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date departureDate,
                                                               @RequestParam("arrivalDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date arrivalDate){
        Schedule schedule = service.getScheduleByRoute(departureAirportIataCode, arrivalAirportIataCode, departureDate, arrivalDate);
        //Add the corresponding Airline object for every flight
        Map<String, Airline> airlines = airlinesManager.getAirlinesMap();
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
