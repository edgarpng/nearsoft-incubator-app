package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.model.PersistableModel;

import java.util.List;

/**
 * Created by edgar on 26/06/14.
 */
public interface Dao<T extends PersistableModel> {

    public List<T> findAll();
    public void saveAll(List<T> elements);
    public void deleteAll();
}
