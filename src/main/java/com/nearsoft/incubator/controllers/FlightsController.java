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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Component
@RequestMapping("/flights")
public class FlightsController extends BaseController {

    @Autowired
    @Qualifier("flightServiceMockImpl")
    private FlightService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Map<String,  List<Flight>> getFlights(/*@RequestParam("fromAirport") String fromAirport*/){
        //TODO: How to pass actual airports and dates?
        HashMap<String, List<Flight>> results = new HashMap<String, List<Flight>>();
        results.put("flights", service.getFlightsByRoute(null, null, null, null));
        return results;
    }

    public FlightService getService() {
        return service;
    }
}
