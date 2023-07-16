package com.mxvc.usuario.Usuarioservice.service.impl;

import com.mxvc.usuario.Usuarioservice.entity.Calificacion;
import com.mxvc.usuario.Usuarioservice.entity.Hotel;
import com.mxvc.usuario.Usuarioservice.entity.User;
import com.mxvc.usuario.Usuarioservice.exception.ResourceNotFoundException;
import com.mxvc.usuario.Usuarioservice.external.HotelService;
import com.mxvc.usuario.Usuarioservice.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mxvc.usuario.Usuarioservice.service.UserService;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class); //para ver los logs

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelService hotelService;

    @Override
    public User saveUser(User user) {
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);

        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found" + userId));

        //comunicacion con el servicio de calificacion
        //sin load balancing
        //Calificacion[] calificacionDelUsuario = restTemplate.getForObject("http://localhost:8083/calificaciones/usuario/" + userId, Calificacion[].class);
        //con load balancing
        Calificacion[] calificacionDelUsuario = restTemplate.getForObject("http://CALIFICACION-SERVICE/calificaciones/usuario/" + userId, Calificacion[].class);
        //
        List<Calificacion> calificaciones = Arrays.stream(calificacionDelUsuario).collect(Collectors.toList());

        List<Calificacion> listaCalificaciones = calificaciones.stream().map(calificacion -> {
            //con rest template
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + calificacion.getHotelId(), Hotel.class); //obtenemos el hotel
            //Hotel hotel = forEntity.getBody(); //obtenemos el hotel
            //logger.info("hotel: " + forEntity.getBody());

            //con feign
            Hotel hotel = hotelService.getHotelById(calificacion.getHotelId());
            calificacion.setHotel(hotel); //seteamos el hotel a la calificacion
            return calificacion;
        }).collect(Collectors.toList());


        //logger.info("calificacionDelUsuario: " + calificacionDelUsuario);

        user.setCalificaciones(listaCalificaciones);

        return user;
    }
}
