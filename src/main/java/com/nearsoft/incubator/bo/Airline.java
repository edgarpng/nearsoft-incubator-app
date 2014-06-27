package com.nearsoft.incubator.bo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by edgar on 18/06/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline extends PersistableEntity   {

    private long id;
    @JsonProperty("fs")
    private String flightStatsId;
    private String name;

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
}
