package com.calificaciones.calificacionespro1.controller;

import com.calificaciones.calificacionespro1.entity.Calificacion;
import com.calificaciones.calificacionespro1.services.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @PostMapping
    public ResponseEntity<Calificacion> create(@RequestBody Calificacion calificacion){
        return ResponseEntity.status(HttpStatus.CREATED).body(calificacionService.create(calificacion));

    }

    @GetMapping
    public ResponseEntity<List<Calificacion>> getAll(){
        return ResponseEntity.ok(calificacionService.getAll());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Calificacion>> getByUsuarioId(@PathVariable String usuarioId){
        return ResponseEntity.ok(calificacionService.getByUsuarioId(usuarioId));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Calificacion>> getByHotelId(@PathVariable String hotelId){
        return ResponseEntity.ok(calificacionService.getByHotelId(hotelId));
    }

}
