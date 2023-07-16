package com.auth.authservicev2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceV2Application {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceV2Application.class, args);
	}

}
