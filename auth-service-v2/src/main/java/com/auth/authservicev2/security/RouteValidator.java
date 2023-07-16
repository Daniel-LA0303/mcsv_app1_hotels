package com.auth.authservicev2.security;


import com.auth.authservicev2.dto.RequestDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidator {

    private List<RequestDto> paths;

    public List<RequestDto> getPaths() {
        return paths;
    }

    public void setPaths(List<RequestDto> paths) {
        this.paths = paths;
    }

    //metodo que valida si la ruta es de administrador
    public boolean isAdmin(RequestDto requestDto){

        return paths.stream().anyMatch(path ->
                Pattern.matches(path.getUri(), requestDto.getUri()) &&
                        path.getMethod().equals(requestDto.getMethod()));

    }
}
