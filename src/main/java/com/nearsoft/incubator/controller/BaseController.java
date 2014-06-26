package com.nearsoft.incubator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by edgar on 5/06/14.
 */
public abstract class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler
    public void handleException(Exception e){
        LOG.error("An exception occurred within a controller:", e);
    }
}
