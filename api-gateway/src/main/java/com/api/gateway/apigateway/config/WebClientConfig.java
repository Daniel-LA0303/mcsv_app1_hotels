package com.api.gateway.apigateway.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /*
    * in this class what we did is to make a
    * configuration for at a certain time called this configuration
    * and be able to communicate with other services
    * */

    //to make any request to other services we need to use the webclient
    @Bean
    @LoadBalanced
    public WebClient.Builder builder(){
        return WebClient.builder();
    }

}
