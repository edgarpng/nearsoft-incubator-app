package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.bo.Airport;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created by edgar on 25/06/14.
 */
@Component("hibernateAirportsDao")
public class AirportsDaoHibernateImpl implements AirportsDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<Airport> getAllAirports() {
        return sessionFactory.getCurrentSession().createQuery("from Airport").list();
    }

    @Override
    public void save(List<Airport> airports) {
        int inserted = 0;
        final int BATCH_SIZE = 50;
        Iterator<Airport> iterator = airports.iterator();
        while(iterator.hasNext()){
            Airport airport = iterator.next();
            sessionFactory.getCurrentSession().save(airport);
            if(++inserted % BATCH_SIZE == 0){
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        }
    }

    @Override
    public void deleteAll() {
        sessionFactory.getCurrentSession().createQuery("delete from Airport").executeUpdate();
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}
