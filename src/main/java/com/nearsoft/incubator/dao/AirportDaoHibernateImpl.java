package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.model.Airport;
import org.springframework.stereotype.Component;

/**
 * Created by edgar on 25/06/14.
 */
@Component("airportDaoHibernateImpl")
public class AirportDaoHibernateImpl extends HibernateDao<Airport> {

    public AirportDaoHibernateImpl(){
        super(Airport.class);
    }
}
