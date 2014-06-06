package com.nearsoft.incubator.controllers;

import com.nearsoft.incubator.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Component
@RequestMapping("/search")
public class SearchController extends BaseController{

    @Autowired
    @Qualifier("flightServiceMockImpl")
    private FlightService service;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getResults(ModelMap model){
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("flights", service.getFlightsByRoute(null, null, null, null));
        return modelAndView;
    }
}
