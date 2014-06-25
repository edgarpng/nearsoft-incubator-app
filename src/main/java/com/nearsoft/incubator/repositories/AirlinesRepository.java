package com.nearsoft.incubator.repositories;

import com.nearsoft.incubator.bo.Airline;

import java.util.Map;

/**
 * Created by edgar on 24/06/14.
 */
public interface AirlinesRepository {
    public Map<String, Airline> getAirlinesMap();
}
