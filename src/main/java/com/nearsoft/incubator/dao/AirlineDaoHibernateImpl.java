package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.bo.Airline;
import org.springframework.stereotype.Component;

/**
 * Created by edgar on 25/06/14.
 */
@Component("airlineDaoHibernateImpl")
public class AirlineDaoHibernateImpl extends HibernateDao<Airline> {

    public AirlineDaoHibernateImpl(){
        super(Airline.class);
    }
}