package com.mxvc.usuario.Usuarioservice.controllers;


import com.mxvc.usuario.Usuarioservice.entity.User;
import com.mxvc.usuario.Usuarioservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){

        User userSaved = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){

        List<User> users = userService.getUsers();

        return ResponseEntity.ok(users);
    }

    int intentos = 1;

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback") // esto es para el manejo de errores
    @Retry(name ="ratingHotelService", fallbackMethod = "ratingHotelFallback") // esto es para el manejo de errores
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        log.info("Intento numero: " + intentos);
        intentos++;
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception exception){

        log.info("El respaldo se ejecuta por el servicio esta inactivo", exception.getMessage());
        User user = User.builder()
                .email("correo@correo.com")
                .name("root")
                .info("Este servicio se ejecuta cuando un servicio falla")
                .userId("1234")
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
