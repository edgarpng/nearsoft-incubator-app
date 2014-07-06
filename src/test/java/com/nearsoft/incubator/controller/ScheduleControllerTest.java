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
    @Autowired
    private String departureAirportUrlParam;
    @Autowired
    private String arrivalAirportUrlParam;
    @Autowired
    private String departureDateUrlParam;
    @Autowired
    private String arrivalDateUrlParam;
    @Autowired
    private String dateFormat;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetSchedule() throws Exception {
        Schedule schedule = new Schedule();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date departureDate = format.parse(departureDateUrlParam);
        Date arrivalDate = format.parse(arrivalDateUrlParam);
        expect(service.getScheduleByRoute(eq(departureAirportUrlParam), eq(arrivalAirportUrlParam),
                eq(departureDate), eq(arrivalDate))).andReturn(schedule).once();
        replay(service);
        mockMvc.perform(get("/schedules")
                    .param("departureAirport", departureAirportUrlParam)
                    .param("arrivalAirport", arrivalAirportUrlParam)
                    .param("departureDate", departureDateUrlParam)
                    .param("arrivalDate", arrivalDateUrlParam)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(not(isEmptyString())));
        verify(service);
    }
}
