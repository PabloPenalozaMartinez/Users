package com.desafio.user.exception;

public class ObjectNotFoundException  extends RuntimeException {
    private String mensaje;

    public ObjectNotFoundException() {}

    public ObjectNotFoundException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }
}