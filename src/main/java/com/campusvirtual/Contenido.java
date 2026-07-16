package com.campusvirtual;

import java.util.UUID;

public class Contenido {
    private final String id;
    private final String titulo;
    private final int orden; // Posición secuencial dentro del curso (ej: 1, 2, 3...)

    public Contenido(String titulo, int orden) {
        validarTitulo(titulo);
        validarOrden(orden);
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo.trim();
        this.orden = orden;
    }

    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new TituloContenidoInvalidoException("El título del contenido no puede estar vacío.");
        }
    }

    private void validarOrden(int orden) {
        if (orden < 1) {
            throw new IllegalArgumentException("El orden del contenido debe ser mayor o igual a 1.");
        }
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getOrden() {
        return orden;
    }
}