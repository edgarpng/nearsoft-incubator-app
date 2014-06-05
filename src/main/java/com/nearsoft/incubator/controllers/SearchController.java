package com.nearsoft.incubator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/search")
public class SearchController {

    @RequestMapping(method = RequestMethod.GET)
    public String getResults(ModelMap model){
        return "search";
    }
}
