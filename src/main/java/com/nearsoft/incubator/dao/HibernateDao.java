package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.model.PersistableModel;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by edgar on 26/06/14.
 */
public abstract class HibernateDao<T extends PersistableModel> implements Dao<T>{

    private static int insertBatchSize = 50;
    private SessionFactory sessionFactory;
    protected Class<T> clazz;

    public HibernateDao(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public List<T> findAll() {
       return (List<T>)sessionFactory.getCurrentSession().createQuery("from " + clazz.getSimpleName()).list();
    }

    @Override
    public void saveAll(List<T> elements) {
        int inserted = 0;
        for(T element : elements){
            sessionFactory.getCurrentSession().save(element);
            if(++inserted % insertBatchSize == 0){
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        }
    }

    @Override
    public void deleteAll() {
        sessionFactory.getCurrentSession().createQuery("delete from " + clazz.getSimpleName()).executeUpdate();
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }

    public final SessionFactory getSessionFactory(){
        return this.sessionFactory;
    }

    public final void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
}
