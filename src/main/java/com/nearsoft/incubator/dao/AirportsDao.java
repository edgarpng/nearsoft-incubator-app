package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.bo.Airport;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by edgar on 16/06/14.
 */
@Component
public interface AirportsDao {
    public List<Airport> getAllAirports();
    public void save(List<Airport> airports);
    public void deleteAll();
}
