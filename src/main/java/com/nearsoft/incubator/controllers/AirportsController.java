package com.nearsoft.incubator.controllers;

import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by edgar on 5/06/14.
 */
@Controller
@Component
@RequestMapping("/airports")
public class AirportsController extends BaseController{

    @Autowired
    @Qualifier("flightServiceImpl")
    private FlightService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Airport> getAllAirports(){
        return service.getAllAirports();
    }

    public FlightService getService() {
        return service;
    }
}
