package com.nearsoft.incubator.service;

import com.nearsoft.incubator.bo.Airline;

import java.util.Map;

/**
 * Created by edgar on 24/06/14.
 */
public interface AirlineService {
    public Map<String, Airline> getAirlinesMap();
}
