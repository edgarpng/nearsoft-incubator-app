package com.nearsoft.incubator.bo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * Created by edgar on 18/06/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline {

    private long id;
    @JsonProperty("fs")
    private String flightStatsId;
    private String name;
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

    public String getFlightStatsId() {
        return flightStatsId;
    }

    public void setFlightStatsId(String flightStatsId) {
        this.flightStatsId = flightStatsId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
