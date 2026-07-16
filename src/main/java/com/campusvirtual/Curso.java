package com.campusvirtual;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String titulo;
    private String descripcion;
    private String estado;
    private List<Modulo> modulos;
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

    public void agregarModulo(Modulo modulo) {
        if (this.modulos.size() >= 30) {
            throw new IllegalStateException("El curso no puede superar los 30 módulos");
        }
        this.modulos.add(modulo);
    }

    public void removerModulo(Modulo modulo) {
        if ("PUBLICADO".equals(this.estado)) {
            throw new IllegalStateException("No se pueden eliminar módulos de un curso publicado");
        }
        this.modulos.remove(modulo);
    }

    public void publicar() {
        if (this.modulos.isEmpty()) {
            throw new IllegalStateException("No se puede publicar un curso sin módulos");
        }
        this.estado = "PUBLICADO";
    }

    public void actualizarInformacion(String nuevoTitulo, String nuevaDescripcion) {
        if (!"EN_BORRADOR".equals(this.estado)) {
            throw new IllegalStateException("Solo se puede actualizar la información en borrador");
        }
        if (nuevoTitulo == null || nuevoTitulo.trim().isEmpty()) {
            throw new TituloInvalidoException("El título no puede estar vacío");
        }
        this.titulo = nuevoTitulo;
        this.descripcion = nuevaDescripcion;
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

    public List<Modulo> getModulos() {
        return modulos;
    }
}