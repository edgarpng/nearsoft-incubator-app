package com.nearsoft.incubator.bo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by edgar on 18/06/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline {

    private long id;
    private String name;
    //Internal identifier for FlightStats
    private String fs;
    private Date creationDate = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
