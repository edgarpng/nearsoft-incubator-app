package com.nearsoft.incubator.managers;

import com.nearsoft.incubator.bo.Airline;

import java.util.Map;

/**
 * Created by edgar on 24/06/14.
 */
public interface AirlinesManager {
    public Map<String, Airline> getAirlinesMap();
}
