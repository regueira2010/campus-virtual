package com.campusvirtual;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String titulo;
    private String descripcion;
    private String estado;
    private List<String> modulos;

    public Curso(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = "EN_BORRADOR";
        this.modulos = new ArrayList<>();
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