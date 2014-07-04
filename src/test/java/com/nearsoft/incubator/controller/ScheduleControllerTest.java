package com.nearsoft.incubator.controller;

import com.nearsoft.incubator.bo.Schedule;
import com.nearsoft.incubator.service.ScheduleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by edgar on 1/07/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath:spring/mvc-beans.xml",
        "classpath:spring/controller-beans-test.xml"
})
public class ScheduleControllerTest {

    @Autowired
    private ScheduleService service;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private static final String DEPARTURE_AIRPORT = "SFO";
    private static final String ARRIVAL_AIRPORT = "LAX";
    private static final String DEPARTURE_DATE = "2014-10-10";
    private static final String ARRIVAL_DATE = "2014-10-11";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetSchedule() throws Exception {
        Schedule schedule = new Schedule();
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date departureDate = format.parse(DEPARTURE_DATE);
        Date arrivalDate = format.parse(ARRIVAL_DATE);
        expect(service.getScheduleByRoute(eq(DEPARTURE_AIRPORT), eq(ARRIVAL_AIRPORT),
                eq(departureDate), eq(arrivalDate))).andReturn(schedule).once();
        replay(service);
        mockMvc.perform(get("/schedules")
                    .param("departureAirport", DEPARTURE_AIRPORT)
                    .param("arrivalAirport", ARRIVAL_AIRPORT)
                    .param("departureDate", DEPARTURE_DATE)
                    .param("arrivalDate", ARRIVAL_DATE)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(not(isEmptyString())));
        verify(service);
    }
}
