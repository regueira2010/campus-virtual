package com.campusvirtual;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProgresoTest {

    @Test // Debe inicializar el progreso del estudiante en 0% y con la lista de completados vacía
    void shouldInitializeWithZeroPercentProgressAndEmptyCompletedListWhenStudentEnrolls() {
        // Arrange
        Course curso = crearCurso("Java Avanzado");
        String estudianteId = "estudiante-123";

        // Act
        Progreso progreso = new Progreso(estudianteId, curso);

        // Assert
        assertEquals(estudianteId, progreso.getEstudianteId());
        assertEquals(curso, progreso.getCurso());
        assertTrue(progreso.getContenidosCompletadosIds().isEmpty(), "La lista de completados debe iniciar vacía");
        assertEquals(0.0, progreso.calcularPorcentajeAvance(), "El porcentaje inicial debe ser 0%");
    }

    @Test // Debe permitir marcar como completado el primer contenido del curso sin restricciones
    void shouldAllowCompletingTheFirstContenidoDirectlyWithoutAnyPredecessors() {
        // Arrange
        Course curso = crearCurso("Java Avanzado");
        Module modulo = new Module("Introducción");
        Content contenido1 = new Content("Variables", 1);
        
        modulo.agregarContenido(contenido1);
        curso.agregarModulo(modulo);
        
        Progreso progreso = new Progreso("estudiante-123", curso);

        // Act
        progreso.completarContenido(contenido1);

        // Assert
        assertTrue(progreso.getContenidosCompletadosIds().contains(contenido1.getId()), "El primer contenido debe marcarse como completado");
        assertEquals(100.0, progreso.calcularPorcentajeAvance(), "Progreso debe subir al 100% al completar la única lección");
    }

    @Test // Debe lanzar una excepción si el estudiante intenta completar un contenido saltándose lecciones previas
    void shouldThrowIllegalStateExceptionWhenAttemptingToCompleteContenidoWithPendingPredecessors() {
        // Arrange
        Course curso = crearCurso("Java Avanzado");
        Module modulo = new Module("Introducción");
        Content contenido1 = new Content("Variables", 1);
        Content contenido2 = new Content("Bucles", 2);
        
        modulo.agregarContenido(contenido1);
        modulo.agregarContenido(contenido2);
        curso.agregarModulo(modulo);
        
        Progreso progreso = new Progreso("estudiante-123", curso);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            progreso.completarContenido(contenido2); // Se salta el contenido 1
        });
        
        assertEquals("No puedes completar este contenido porque tienes lecciones previas pendientes.", exception.getMessage());
        assertFalse(progreso.getContenidosCompletadosIds().contains(contenido2.getId()), "El contenido 2 no debió marcarse");
    }

    @Test // Debe permitir completar un contenido avanzado si todos los contenidos anteriores ya fueron finalizados
    void shouldAllowCompletingContenidoSuccessfullyWhenAllPreviousContenidosAreAlreadyCompleted() {
        // Arrange
        Course curso = crearCurso("Java Avanzado");
        Module modulo = new Module("Introducción");
        Content contenido1 = new Content("Variables", 1);
        Content contenido2 = new Content("Bucles", 2);
        
        modulo.agregarContenido(contenido1);
        modulo.agregarContenido(contenido2);
        curso.agregarModulo(modulo);
        
        Progreso progreso = new Progreso("estudiante-123", curso);

        // Act
        progreso.completarContenido(contenido1); // Completa el 1
        progreso.completarContenido(contenido2); // Ahora sí debe permitir el 2

        // Assert
        assertTrue(progreso.getContenidosCompletadosIds().contains(contenido2.getId()), "El contenido 2 debió completarse sin problemas");
        assertEquals(100.0, progreso.calcularPorcentajeAvance(), "Ambas lecciones listas equivalen al 100%");
    }

    @Test // No debe lanzar error ni alterar el progreso si se intenta marcar como completado un contenido ya finalizado
    void shouldIgnoreRequestAndNotDuplicateEntryWhenContenidoIsAlreadyMarkedAsCompleted() {
        // Arrange
        Course curso = crearCurso("Java Avanzado");
        Module modulo = new Module("Introducción");
        Content contenido1 = new Content("Variables", 1);
        
        modulo.agregarContenido(contenido1);
        curso.agregarModulo(modulo);
        
        Progreso progreso = new Progreso("estudiante-123", curso);

        // Act
        progreso.completarContenido(contenido1);
        progreso.completarContenido(contenido1); // Intento duplicado

        // Assert
        assertEquals(1, progreso.getContenidosCompletadosIds().size(), "No debe duplicarse el ID en la lista");
        assertEquals(100.0, progreso.calcularPorcentajeAvance());
    }

    @Test // Debe calcular con precisión el porcentaje de avance basándose en la cantidad de contenidos completados
    void shouldCalculateAccurateProgressPercentageBasedOnTotalCompletedContenidos() {
        // Arrange
        Course curso = crearCurso("Java Avanzado");
        Module modulo = new Module("Introducción");
        Content contenido1 = new Content("Variables", 1);
        Content contenido2 = new Content("Bucles", 2);
        Content contenido3 = new Content("Métodos", 3);
        Content contenido4 = new Content("Clases", 4);
        
        modulo.agregarContenido(contenido1);
        modulo.agregarContenido(contenido2);
        modulo.agregarContenido(contenido3);
        modulo.agregarContenido(contenido4);
        curso.agregarModulo(modulo);
        
        Progreso progreso = new Progreso("estudiante-123", curso);

        // Act
        progreso.completarContenido(contenido1);
        progreso.completarContenido(contenido2); // 2 de 4 completados

        // Assert
        assertEquals(50.0, progreso.calcularPorcentajeAvance(), "El progreso calculado debe ser exactamente del 50%");
    }

    @Test // Debe retornar exactamente 0% de progreso si el curso al que se inscribió no tiene contenidos cargados
    void shouldReturnZeroPercentProgressWhenCourseDoesNotContainAnyContenidos() {
        // Arrange
        Course curso = crearCurso("Java Vacío"); // Curso creado sin módulos ni contenidos
        Progreso progreso = new Progreso("estudiante-123", curso);

        // Act
        double porcentaje = progreso.calcularPorcentajeAvance();

        // Assert
        assertEquals(0.0, porcentaje, "Evita división por cero y retorna 0.0 de forma segura");
    }

    private Course crearCurso(String titulo) {
        NotificationService notificador = org.mockito.Mockito.mock(NotificationService.class);
        return new Course(titulo, "Descripción", notificador);
    }
}