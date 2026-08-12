package com.campusvirtual.domain.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Progress {
    private final String studentId;
    private final Course course;
    private final List<String> completedContentIds;

    public Progress(String studentId, Course course) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        this.studentId = studentId;
        this.course = course;
        this.completedContentIds = new ArrayList<>();
    }

    // Regla: Completar contenido en orden secuencial
    public void completeContent(Content contentToRecord) {
        // Si ya está completado, no hacemos nada para evitar duplicados
        if (completedContentIds.contains(contentToRecord.getId())) {
            return;
        }

        // Obtenemos todos los temas de todos los módulos del curso
        List<Content> allContents = getAllCourseContents();

        // Validamos que no queden lecciones previas pendientes
        for (Content evaluatedContent : allContents) {
            if (evaluatedContent.getOrder() < contentToRecord.getOrder()) {
                if (!completedContentIds.contains(evaluatedContent.getId())) {
                    throw new IllegalStateException("Cannot complete this content because previous lessons are pending.");
                }
            }
        }

        // Si todo está en orden, lo marcamos como completado
        this.completedContentIds.add(contentToRecord.getId());
    }

    // Regla: Calcular porcentaje de progreso del alumno
    public double calculateProgressPercentage() {
        List<Content> allContents = getAllCourseContents();
        if (allContents.isEmpty()) {
            return 0.0;
        }

        double completed = completedContentIds.size();
        double total = allContents.size();

        return (completed / total) * 100.0;
    }

    // Método auxiliar para juntar todos los contenidos del curso
    private List<Content> getAllCourseContents() {
        List<Content> unifiedList = new ArrayList<>();
        for (Module module : course.getModules()) {
            unifiedList.addAll(module.getContents());
        }
        return unifiedList;
    }

    // Getters estándar
    public String getStudentId() {
        return studentId;
    }

    public Course getCourse() {
        return course;
    }

    public List<String> getCompletedContentIds() {
        return Collections.unmodifiableList(completedContentIds);
    }
}