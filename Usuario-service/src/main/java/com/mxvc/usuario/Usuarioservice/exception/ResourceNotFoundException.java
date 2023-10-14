package com.mxvc.usuario.Usuarioservice.exception;

public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(){
        super("There is a Error, resource not found: ");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
