package com.servicio.registro.registry.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Application {

	/*
		In this case, the main class is the Eureka server itself.
		We can run the application as a normal Spring Boot application.
		The configuration is very simple, we just need to add the @EnableEurekaServer annotation to the main class.
		In the application.yml file, we need to set the server port and the application name that will be used by the clients to discover the server.
	*/
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
