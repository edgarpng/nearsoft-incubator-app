package com.nearsoft.incubator.util;

import com.nearsoft.incubator.bo.Airline;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by edgar on 20/06/14.
 */
public class Airlines {
    public static Map<String, Airline> toAirlinesMap(Collection<Airline> airlines){
        Map<String, Airline> map = new HashMap<>(airlines.size());
        for(Airline airline : airlines){
            map.put(airline.getFlightStatsId(), airline);
        }
        return map;
    }
}
