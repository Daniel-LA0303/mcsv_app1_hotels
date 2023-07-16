package com.mxvc.usuario.Usuarioservice.service;

import com.mxvc.usuario.Usuarioservice.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getUsers();

    User getUserById(String userId);


}
