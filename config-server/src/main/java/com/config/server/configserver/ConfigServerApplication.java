package com.config.server.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	/*
	* In this service we have a config with github repository where we have the configuration file for connect to the registy server
	* are files called application-prod.yml, application-prod.yml and application.yml, this files require that a service instead
	* of connecting directly to the server go through this service
	 * */
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
