package com.calificaciones.calificacionespro1.services.impl;

import com.calificaciones.calificacionespro1.entity.Calificacion;
import com.calificaciones.calificacionespro1.repository.CalificacionRepository;
import com.calificaciones.calificacionespro1.services.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionServiceImpl implements CalificacionService{

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Override
    public Calificacion create(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    @Override
    public List<Calificacion> getAll() {
        return calificacionRepository.findAll();
    }

    @Override
    public List<Calificacion> getByUsuarioId(String usuarioId) {
        return calificacionRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Calificacion> getByHotelId(String hotelId) {
        return calificacionRepository.findByHotelId(hotelId);
    }
}
