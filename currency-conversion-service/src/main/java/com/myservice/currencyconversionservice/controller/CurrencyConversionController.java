package com.myservice.currencyconversionservice.controller;

import com.myservice.currencyconversionservice.entity.CurrencyConversion;
import com.myservice.currencyconversionservice.service.CurrencyConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    private final CurrencyConversionService currencyConversionService;

    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConversion> convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        return new ResponseEntity<>(currencyConversionService.convertCurrency(from, to, quantity), HttpStatus.OK);
    }

}
