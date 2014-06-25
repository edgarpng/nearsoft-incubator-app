package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.bo.Airline;

import java.util.Map;

/**
 * Created by edgar on 20/06/14.
 */
public interface AirlinesDao {
    public Map<String, Airline> getAirlinesMap();
    public void save(Map<String, Airline> airlines);
    public void deleteAll();
}
