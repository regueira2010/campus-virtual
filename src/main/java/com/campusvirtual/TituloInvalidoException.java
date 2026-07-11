package com.campusvirtual;

public class TituloInvalidoException extends RuntimeException {
    public TituloInvalidoException(String mensaje) {
        super(mensaje);
    }
}