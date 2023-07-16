package com.calificaciones.calificacionespro1.services;

import com.calificaciones.calificacionespro1.entity.Calificacion;

import java.util.List;

public interface CalificacionService {

    Calificacion create(Calificacion calificacion);

    List<Calificacion> getAll();

    List<Calificacion> getByUsuarioId(String usuarioId);

    List<Calificacion> getByHotelId(String hotelId);

}
