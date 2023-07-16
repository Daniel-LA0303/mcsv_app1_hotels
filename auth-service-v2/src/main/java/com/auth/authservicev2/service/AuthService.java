package com.auth.authservicev2.service;

import com.auth.authservicev2.dto.AuthUserDto;
import com.auth.authservicev2.dto.NewUserDto;
import com.auth.authservicev2.dto.RequestDto;
import com.auth.authservicev2.dto.TokenDto;
import com.auth.authservicev2.entity.AuthUser;
import com.auth.authservicev2.repository.AuthUserRepository;
import com.auth.authservicev2.security.JtwProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JtwProvider jtwProvider;

    // aqui se guarda el usuario
    public AuthUser save(NewUserDto authUserDto){
        Optional<AuthUser> user = authUserRepository.findByUserName(authUserDto.getUserName());

        if (user.isPresent()){
            throw new RuntimeException("User already exists");
        }

        // aqui se encripta la contraseña
        String password = passwordEncoder.encode(authUserDto.getPassword());

        // aqui se crea el usuario
        AuthUser authUser = AuthUser.builder()
                .userName(authUserDto.getUserName())
                .password(password)
                .role(authUserDto.getRole())
                .build();

        return authUserRepository.save(authUser);
    }

    // aqui se loguea el usuario
    public TokenDto login(AuthUserDto authUserDto){
        Optional<AuthUser> user = authUserRepository.findByUserName(authUserDto.getUserName());

        if (!user.isPresent()){
            throw new RuntimeException("User does not exist");
        }

        // aqui se valida la contraseña
        if (!passwordEncoder.matches(authUserDto.getPassword(), user.get().getPassword())){
            throw new RuntimeException("Invalid credentials");

        }

        // Generar el token JWT
        // Generar el token JWT utilizando el método createToken
        String token = jtwProvider.createToken(user.get());

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);

        return tokenDto;

    }

    // aqui se valida el token
    public TokenDto validate(String token, RequestDto requestDto){
        // aqui se valida el token
        if (!jtwProvider.validate(token, requestDto)){
            throw new RuntimeException("Invalid token");
        }

        String userName = jtwProvider.getUserNameFromToken(token);

        if(!authUserRepository.findByUserName(userName).isPresent()){
            throw new RuntimeException("User does not exist");
        }
        return new TokenDto(token);
    }
}
