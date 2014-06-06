package com.nearsoft.incubator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController{

    @RequestMapping(method = RequestMethod.GET)
    public String get(ModelMap model) {
        return "home";
    }
}