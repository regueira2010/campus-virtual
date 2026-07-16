package com.campusvirtual;

import java.util.ArrayList;
import java.util.List;

public class Modulo {
    private String nombre;
    private final List<Contenido> contenidos; // Para almacenar las lecciones

    public Modulo(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del módulo no puede estar vacío");
        }
        this.nombre = nombre;
        this.contenidos = new ArrayList<>(); // Inicializamos la lista
    }

    // Método para añadir lecciones al módulo
    public void agregarContenido(Contenido contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser nulo");
        }
        this.contenidos.add(contenido);
    }

    public String getNombre() { 
        return nombre; 
    }

    // Getter para que la clase Progreso pueda consultar las lecciones
    public List<Contenido> getContenidos() {
        return contenidos;
    }
}