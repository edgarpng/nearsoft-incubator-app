package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.util.Airlines;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar on 25/06/14.
 */
@Component("airlineDaoHibernateImpl")
public class AirlineDaoHibernateImpl implements AirlineDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Map<String, Airline> getAirlinesMap() {
        List<Airline> airlines = sessionFactory.getCurrentSession().createQuery("from Airline").list();
        return Airlines.toAirlinesMap(airlines);
    }

    @Override
    public void save(Map<String, Airline> airlines) {
        int inserted = 0;
        final int BATCH_SIZE = 50;
        Iterator<Airline> iterator = airlines.values().iterator();
        while(iterator.hasNext()){
            Airline airline = iterator.next();
            sessionFactory.getCurrentSession().save(airline);
            if(++inserted % BATCH_SIZE == 0){
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        }
    }

    @Override
    public void deleteAll() {
        sessionFactory.getCurrentSession().createQuery("delete from Airline").executeUpdate();
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}