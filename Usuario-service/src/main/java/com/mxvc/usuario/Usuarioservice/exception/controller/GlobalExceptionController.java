package com.mxvc.usuario.Usuarioservice.exception.controller;


import com.mxvc.usuario.Usuarioservice.exception.ResourceNotFoundException;
import com.mxvc.usuario.Usuarioservice.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice // This annotation is used to handle exceptions globally
public class GlobalExceptionController {

    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){

        String message = resourceNotFoundException.getMessage();

        ApiResponse apiResponse = ApiResponse.builder()
                .message(message)
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
