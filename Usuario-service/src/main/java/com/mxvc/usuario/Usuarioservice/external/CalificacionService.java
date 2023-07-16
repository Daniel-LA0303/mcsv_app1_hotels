package com.mxvc.usuario.Usuarioservice.external;

import com.mxvc.usuario.Usuarioservice.entity.Calificacion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "CALIFICACION-SERVICE")
public interface CalificacionService {

    @PostMapping
    public ResponseEntity<Calificacion> saveCalificacion(Calificacion calificacion);

    @PostMapping("/{calificacionId}")
    public ResponseEntity<Calificacion> actualizarCalificacion(@PathVariable String calificacionId, Calificacion calificacion);

    @DeleteMapping("/{calificacionId}")
    public void deleteCalificacion(@PathVariable String calificacionId);

}
