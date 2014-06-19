package com.nearsoft.incubator.bo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by edgar on 18/06/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline {

    private String name;
    //Internal identifier for FlightStats
    private String fs;

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
}
