package com.nearsoft.incubator.controllers;

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
        return service.getScheduleByRoute(departureAirport, arrivalAirport, departureDate, arrivalDate);
    }
}
