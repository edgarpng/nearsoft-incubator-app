package com.nearsoft.incubator.service;

import com.nearsoft.incubator.model.Schedule;

import java.util.Date;

/**
 * Created by edgar on 26/06/14.
 */
public interface ScheduleService {
    public Schedule getScheduleByRoute(String departureAirportIataCode,
                                       String arrivalAirportIataCode, Date departure, Date arrival);
}
