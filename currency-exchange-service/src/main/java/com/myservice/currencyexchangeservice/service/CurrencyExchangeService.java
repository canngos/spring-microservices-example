package com.myservice.currencyexchangeservice.service;

import com.myservice.currencyexchangeservice.repository.CurrencyExchangeRepository;
import com.myservice.currencyexchangeservice.response.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyExchangeService {

    @Autowired
    private Environment environment; // to get the port number. because we are going to use this service in multiple instances. load balancing.
    private final CurrencyExchangeRepository repository;

    public CurrencyExchangeService(CurrencyExchangeRepository repository) {
        this.repository = repository;
    }

    public CurrencyExchange retrieveExchangeValue(String from, String to) {
        Optional<CurrencyExchange> currencyExchangeOptional = repository.findByFromAndTo(from, to);
        if (currencyExchangeOptional.isEmpty()) {
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }
        CurrencyExchange currencyExchange = currencyExchangeOptional.get();
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
