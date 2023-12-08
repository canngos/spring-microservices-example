package com.myservice.currencyconversionservice.service;

import com.myservice.currencyconversionservice.entity.CurrencyConversion;
import com.myservice.currencyconversionservice.service.feign.FeignExchangeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyConversionService {
    private final FeignExchangeService feignExchangeService;

    public CurrencyConversionService(FeignExchangeService feignExchangeService) {
        this.feignExchangeService = feignExchangeService;
    }

    public CurrencyConversion convertCurrency(String from, String to, BigDecimal quantity) {
        CurrencyConversion currencyConversion;
        try {
            currencyConversion = feignExchangeService.retrieveExchangeValue(from, to);
        }catch (Exception e){
            throw new RuntimeException("Error while calling currency exchange service");
        }

        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
        return currencyConversion;
    }
}
