package com.myservices.limitsservice.controller;

import com.myservices.limitsservice.bean.Limits;
import com.myservices.limitsservice.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;
    // Commented out lines below are similar to the above line. They both use values in application.properties file.
    // The above line is using the @ConfigurationProperties annotation.
/*
    @Value("${limits-service.minimum}")
    private int min;
    @Value("${limits-service.maximum}")
    private int max;

 */

    @GetMapping("/limits")
    public Limits retrieveLimits() {
        return new Limits(configuration.getMinimum(), configuration.getMaximum());
        //return new Limits(min, max);
    }
}
