package com.desafio.user.exception;

public class ValidationDataException  extends RuntimeException {
    private String mensaje;

    public ValidationDataException() {}

    public ValidationDataException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }
}