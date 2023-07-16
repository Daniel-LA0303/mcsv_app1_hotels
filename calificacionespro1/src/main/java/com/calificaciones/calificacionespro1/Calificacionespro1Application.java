package com.calificaciones.calificacionespro1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Calificacionespro1Application {

	public static void main(String[] args) {
		SpringApplication.run(Calificacionespro1Application.class, args);
	}

}
