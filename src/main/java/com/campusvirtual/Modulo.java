package com.campusvirtual;

public class Modulo {
    private String nombre;

    public Modulo(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del módulo no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
}