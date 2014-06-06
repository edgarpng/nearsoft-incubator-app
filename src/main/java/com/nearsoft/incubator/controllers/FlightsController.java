package com.nearsoft.incubator.controllers;

import com.nearsoft.incubator.bo.Flight;
import com.nearsoft.incubator.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Component
@RequestMapping("/flights")
public class FlightsController extends BaseController {

    @Autowired
    @Qualifier("flightServiceMockImpl")
    private FlightService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Flight> getFlights(/*@RequestParam("fromAirport") String fromAirport*/){
        //TODO: How to pass actual airports and dates?
        return service.getFlightsByRoute(null, null, null, null);
    }

    public FlightService getService() {
        return service;
    }
}
