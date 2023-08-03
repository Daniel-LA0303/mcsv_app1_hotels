package com.api.gateway.apigateway.config;


import com.api.gateway.apigateway.dto.RequestDto;
import com.api.gateway.apigateway.dto.TokenDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {


    private WebClient.Builder webClient; //web client is a class provided by spring webflux that allows us to make requests to other services

    //construtor
    /*
    * El constructor realiza dos tareas principales:
    * */
    public AuthFilter(WebClient.Builder webClient) {
        super(Config.class); //Esto establece la clase de configuración que se utilizará para configurar instancias de este filtro.
        this.webClient = webClient; //lo que permite que el filtro acceda a las capacidades de cliente web proporcionadas por WebClient para hacer solicitudes a otros servicios.
    }

    //programacion reactiva
    @Override
    public GatewayFilter apply(Config config) {
        return ((((exchange, chain) -> { // lambda expression
            //exchange represents the actual request and chain represents the chain of filters that will be applied to the request.
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) // here we are checking if the request contains the authorization header
                return onError(exchange, HttpStatus.BAD_REQUEST);
            //here we get the token from the header
            String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            //here we divide the token into two parts and we check if the token is format Bearer token
            String[] chunks = tokenHeader.split(" ");
            if (chunks.length != 2 || !chunks[0].equals("Bearer")) //here we checked if the token is in the correct format (Bearer token)
                return onError(exchange, HttpStatus.BAD_REQUEST);
            //finally we make a request to the auth service to validate the token
            return webClient.build()//we connnect to the auth service
                    .post() //request type
                    .uri("http://auth-service/auth/validate?token=" + chunks[1])//this is our endpoint with the uri
                                        //in request dto  this is the uri                  and this is the method
                    .bodyValue(new RequestDto(exchange.getRequest().getPath().toString(), exchange.getRequest().getMethod().toString())) //here we send the request to the auth service
                    //we get the response from the auth service with post method
                    .retrieve()
                    .bodyToMono(TokenDto.class)
                    //we process the response
                    .map(t -> {
                        t.getToken();
                        return exchange;
                    }).flatMap(chain::filter); ///here we permit the request to continue its way
        })));
    }

    //error management
    public Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }


    public static class Config{
        // Put the configuration properties
    }

}
