package com.myservice.cloudconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer // This annotation is used to enable the config server.
public class CloudConfigServerApplication {

	public static void main(String[] args) {
		System.out.print(String.join("-", "a"));
		SpringApplication.run(CloudConfigServerApplication.class, args);
	}

}
