package com.myservice.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    // with retry annotation, it will try 3 times and if all 3 fails, then it will throw exception to the client. 3 is the default value
    //@Retry(name = "default")

    // we can also specify the number of retries. In properties file we specify the number of retries for sample-api name
    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")

    // with circuit breaker annotation, if the api fails repeatedly(threshold), then return the fallback response to the client without calling the api
    // With this way, load on the api will be reduced
    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")

    // with rate limiter annotation, we can limit the number of calls to the api
    // example in default: in 10 seconds, we can only call the api 10000 times
    //@RateLimiter(name = "default")

    // with bulkhead annotation, we can limit the number of concurrent calls to the api. (üst üste kaç tane çağrı yapılabilir)
    //@Bulkhead(name = "default")
    public String sampleApi() {
        logger.info("Sample api call received");
        ResponseEntity<String> resp = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return resp.getBody();
    }

    public String hardcodedResponse(Exception ex) {
        return "fallback-response";
    }
}
