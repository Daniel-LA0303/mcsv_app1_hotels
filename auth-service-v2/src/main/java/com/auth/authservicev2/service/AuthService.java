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
        //first we check if the user already exists
        Optional<AuthUser> user = authUserRepository.findByUserName(authUserDto.getUserName());
        //if the user exists we throw an exception
        if (user.isPresent()){
            throw new RuntimeException("User already exists");
        }

        // here we encode the password
        String password = passwordEncoder.encode(authUserDto.getPassword());

        // here we create the user
        AuthUser authUser = AuthUser.builder()
                .userName(authUserDto.getUserName())
                .password(password)
                .role(authUserDto.getRole())
                .build();

        return authUserRepository.save(authUser);
    }

    // here we login
    public TokenDto login(AuthUserDto authUserDto){
        Optional<AuthUser> user = authUserRepository.findByUserName(authUserDto.getUserName());

        if (!user.isPresent()){
            throw new RuntimeException("User does not exist");
        }

        // here we validate the password
        if (!passwordEncoder.matches(authUserDto.getPassword(), user.get().getPassword())){
            throw new RuntimeException("Invalid credentials");

        }

        // generate the JWT token using the createToken method
        String token = jtwProvider.createToken(user.get());

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);

        return tokenDto;

    }

    // aqui se valida el token
    public TokenDto validate(String token, RequestDto requestDto){
        // here we validate the token with the validate method from the JtwProvider class
        if (!jtwProvider.validate(token, requestDto)){
            throw new RuntimeException("Invalid token");
        }
        //we get the username from the token
        String userName = jtwProvider.getUserNameFromToken(token);

        //we check if the user exists
        if(!authUserRepository.findByUserName(userName).isPresent()){
            throw new RuntimeException("User does not exist");
        }
        return new TokenDto(token);
    }
}
