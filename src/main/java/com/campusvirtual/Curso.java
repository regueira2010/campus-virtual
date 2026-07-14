package com.campusvirtual;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String titulo;
    private String descripcion;
    private String estado;
    private List<String> modulos;
    private Notificador notificador;

    public Curso(String titulo, String descripcion, Notificador notificador) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new TituloInvalidoException("El título del curso no puede estar vacío");
        }

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = "EN_BORRADOR";
        this.modulos = new ArrayList<>();
        this.notificador = notificador;

        notificador.enviar("admin@campus.com", "Curso creado: " + titulo);
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public List<String> getModulos() {
        return modulos;
    }
}