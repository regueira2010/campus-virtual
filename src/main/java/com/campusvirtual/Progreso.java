package com.campusvirtual;

import java.util.ArrayList;
import java.util.List;

public class Progreso {
    private final String estudianteId;
    private final Curso curso;
    private final List<String> contenidosCompletadosIds; // Guarda los IDs de los contenidos finalizados

    public Progreso(String estudianteId, Curso curso) {
        if (estudianteId == null || estudianteId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del estudiante no puede estar vacío.");
        }
        if (curso == null) {
            throw new IllegalArgumentException("El curso no puede ser nulo.");
        }
        this.estudianteId = estudianteId;
        this.curso = curso;
        this.contenidosCompletadosIds = new ArrayList<>();
    }

    // --- REGLA DE NEGOCIO CLAVE: COMPLETAR CONTENIDO SECUENCIAL ---
    public void completarContenido(Contenido contenidoAGrabar) {
        // Si ya está completado, se ignora de forma segura sin duplicar ni lanzar error
        if (contenidosCompletadosIds.contains(contenidoAGrabar.getId())) {
            return;
        }

        // Recolectamos todos los contenidos de todos los módulos del curso
        List<Contenido> todosLosContenidos = unificarContenidosDelCurso();

        // Validamos si existe algún contenido previo que aún esté pendiente
        for (Contenido contenidoEvaluado : todosLosContenidos) {
            if (contenidoEvaluado.getOrden() < contenidoAGrabar.getOrden()) {
                if (!contenidosCompletadosIds.contains(contenidoEvaluado.getId())) {
                    throw new IllegalStateException("No puedes completar este contenido porque tienes lecciones previas pendientes.");
                }
            }
        }

        // Si pasó la validación (o es el primer contenido del curso), se marca como completado
        this.contenidosCompletadosIds.add(contenidoAGrabar.getId());
    }

    // --- REGLA DE NEGOCIO: CALCULAR PORCENTAJE DE AVANCE ---
    public double calcularPorcentajeAvance() {
        List<Contenido> todosLosContenidos = unificarContenidosDelCurso();
        if (todosLosContenidos.isEmpty()) {
            return 0.0;
        }
        
        double completados = contenidosCompletadosIds.size();
        double total = todosLosContenidos.size();
        
        return (completados / total) * 100.0;
    }

    // Método auxiliar privado para extraer los contenidos en orden
    private List<Contenido> unificarContenidosDelCurso() {
        List<Contenido> listaUnificada = new ArrayList<>();
        for (Modulo modulo : curso.getModulos()) {
            listaUnificada.addAll(modulo.getContenidos());
        }
        return listaUnificada;
    }

    // --- GETTERS ---
    public String getEstudianteId() { 
        return estudianteId; 
    }
    
    public Curso getCurso() { 
        return curso; 
    }
    
    public List<String> getContenidosCompletadosIds() { 
        return contenidosCompletadosIds; 
    }
}