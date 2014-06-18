package com.nearsoft.incubator.controllers;

import com.nearsoft.incubator.bo.Airport;
import com.nearsoft.incubator.dao.AirportsDao;
import com.nearsoft.incubator.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    @Qualifier("jdbcAirportsDao")
    private AirportsDao airportsDao;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Airport> getAllAirports(){
        List<Airport> airports = airportsDao.getAllAirports();
        if(airports.isEmpty()){
            airports = service.getAllAirports();
            airportsDao.save(airports);
        }
        return airports;
    }
}