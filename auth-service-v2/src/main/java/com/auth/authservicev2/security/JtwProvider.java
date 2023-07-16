package com.auth.authservicev2.security;

import com.auth.authservicev2.dto.RequestDto;
import com.auth.authservicev2.entity.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JtwProvider {

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    @Autowired
    private RouteValidator routeValidator;

    public String createToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();

        claims = Jwts.claims().setSubject(authUser.getUserName());
        claims.put("id", authUser.getId());
        claims.put("role", authUser.getRole());

        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, "secret")
                .compact();

    }

    public boolean validate(String token, RequestDto requestDto){

        try{
            Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
        }catch(Exception e){
            return false;
        }

        if(!isAdmin(token) && routeValidator.isAdmin(requestDto)){
            return false;
        }
        return true;
    }

    private boolean isAdmin(String token){
        Claims claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
        Object roleObject = claims.get("role");

        return "admin".equals(roleObject);

    }

    public String getUserNameFromToken(String token){

        try{
            return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody().getSubject();
        }catch(Exception e){
            return "bad token";

        }
    }
}
