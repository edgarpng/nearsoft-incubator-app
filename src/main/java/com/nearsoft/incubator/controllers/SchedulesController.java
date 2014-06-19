package com.nearsoft.incubator.controllers;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.bo.Flight;
import com.nearsoft.incubator.bo.Schedule;
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

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Schedule getFlights(@RequestParam("departureAirport") String departureAirport,
                                                               @RequestParam("arrivalAirport") String arrivalAirport,
                                                               @RequestParam("departureDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date departureDate,
                                                               @RequestParam("arrivalDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date arrivalDate){
        Schedule schedule =  service.getScheduleByRoute(departureAirport, arrivalAirport, departureDate, arrivalDate);
        //Decorate each flight with an Airline object with name
        setAirlineOnFlights(schedule.getDepartureFlights());
        setAirlineOnFlights(schedule.getArrivalFlights());
        return schedule;
    }

    private void setAirlineOnFlights(List<Flight> flights){
        Map<String, Airline> airlinesMap = service.getAirlinesMap();
        for(Flight flight: flights){
            Airline airline = airlinesMap.get(flight.getCarrierFsCode());
            flight.setAirline(airline);
        }
    }
}
