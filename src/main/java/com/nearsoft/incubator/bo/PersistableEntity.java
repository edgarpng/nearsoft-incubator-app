package com.nearsoft.incubator.bo;

import java.util.Date;

/**
 * Created by edgar on 26/06/14.
 */
public abstract class PersistableEntity {

    private Date creationDate = new Date();

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
