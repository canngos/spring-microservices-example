package com.myservice.currencyconversionservice.service.feign;

import com.myservice.currencyconversionservice.entity.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// name is the application name of the service we want to call
//@FeignClient(name = "currency-exchange", url = "localhost:8000")

// we dont provide url anymore because we are using eureka naming server. We just provide the application name
// Has load balancing feature by default. If we have multiple instances of currency-exchange service, it will load balance between them
@FeignClient(name = "currency-exchange")
public interface FeignExchangeService {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);


}
