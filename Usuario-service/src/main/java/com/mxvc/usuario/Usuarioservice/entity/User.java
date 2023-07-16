package com.mxvc.usuario.Usuarioservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="users")
public class User {

    @Id
    @Column(name="id")
    private String userId;

    @Column(name="name", length = 20)
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="info")
    private String info;

    @Transient
    private List<Calificacion> calificaciones = new ArrayList<>();

}
