package com.nearsoft.incubator.controller;

import com.nearsoft.incubator.model.Airport;
import com.nearsoft.incubator.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AirportController extends BaseController{

    @Autowired
    private AirportService airportService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Airport> getAllAirports(){return airportService.getAllAirports();}
}