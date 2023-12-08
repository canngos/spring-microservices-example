package com.myservice.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                // if user hits /get, redirect to http://httpbin.org:80
                .route(f -> f.path("/get")
                        // we can add headers, params, etc. with filters
                        .filters(g -> g
                                .addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))

                // we can create routes for each microservice. But spring.cloud.gateway.discovery.locator.enabled=true is an easy way to do this
                // With these routes, try to access http://localhost:8765/currency-exchange/from/USD/to/EUR
                // Also try to access http://localhost:8765/currency-conversion/from/USD/to/EUR/quantity/10

                .route(f -> f.path("/currency-exchange/**") // if user goes to resource starts with /currency-exchange
                        .uri("lb://currency-exchange")) // redirect to load balanced currency-exchange service from eureka naming server
                .route(f -> f.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                // below route is custom. we can rewrite the path
                // To understand, go to http://localhost:8765/para-birimi-degis/from/USD/to/EUR/quantity/10
                .route(f -> f.path("/para-birimi-degis/**")
                        .filters(g -> g.rewritePath(
                                "/para-birimi-degis/(?<segment>.*)",
                                "/currency-conversion/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
