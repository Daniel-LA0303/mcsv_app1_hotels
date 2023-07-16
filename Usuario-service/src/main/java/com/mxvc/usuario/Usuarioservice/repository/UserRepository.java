package com.mxvc.usuario.Usuarioservice.repository;

import com.mxvc.usuario.Usuarioservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
