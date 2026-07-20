package com.campusvirtual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CursoTest {

    @Test // Debe crear un curso correctamente con sus valores por defecto y notificar al administrador
    void shouldCreateCourseWithDefaultValues() {
        // Arrange
        NotificadorStub notificador = new NotificadorStub();
        String titulo = "Certificación React";
        String descripcion = "Curso completo de React desde cero";

        // Act
        Course curso = new Course(titulo, descripcion, notificador);

        // Assert - estado del curso
        assertNotNull(curso, "El curso no debería ser nulo");
        assertEquals(titulo, curso.getTitulo(), "El título debería coincidir");
        assertEquals(descripcion, curso.getDescripcion(), "La descripción debería coincidir");
        assertEquals("EN_BORRADOR", curso.getEstado(), "El estado inicial debería ser EN_BORRADOR");
        assertTrue(curso.getModulos().isEmpty(), "La lista de módulos debería estar vacía");

        // Assert - verificar que el notificador fue llamado correctamente
        assertTrue(notificador.fueLlamado(), "El notificador debería haber sido llamado");
        assertEquals("admin@campus.com", notificador.getUltimoDestinatario(),
                "El destinatario debería ser admin@campus.com");
        assertEquals("Curso creado: " + titulo, notificador.getUltimoMensaje(), "El mensaje debería coincidir");
    }

    @ParameterizedTest // Debe lanzar excepción cuando se intenta crear un curso con un título vacío o con solo espacios
    @ValueSource(strings = { "", "   ", "  " })
    void shouldThrowExceptionWhenTituloIsInvalid(String tituloInvalido) {
        // Arrange
        NotificadorStub notificador = new NotificadorStub();
        String descripcion = "Descripción válida";

        // Act & Assert
        assertThrows(InvalidTitleException.class, () -> {
            new Course(tituloInvalido, descripcion, notificador);
        });
    }

    @Test // Debe lanzar excepción al intentar publicar un curso que no tiene módulos
    void shouldThrowExceptionWhenPublishingCourseWithoutAnyModules() {
        NotificadorStub notificador = new NotificadorStub();
        Course curso = new Course("React Básico", "Descripción", notificador);

        assertThrows(IllegalStateException.class, () -> {
            curso.publicar();
        });
    }

    @Test // Debe cambiar el estado a PUBLICADO cuando el curso tiene al menos un módulo
    void shouldChangeStatusToPublishedWhenCourseHasAtLeastOneModule() {
        NotificadorStub notificador = new NotificadorStub();
        Course curso = new Course("React Básico", "Descripción", notificador);
        Module modulo = new Module("Introducción a React");

        curso.agregarModulo(modulo);
        curso.publicar();

        assertEquals("PUBLICADO", curso.getEstado());
    }

    @Test // Debe lanzar excepción al intentar agregar más de 30 módulos al curso
    void shouldThrowExceptionWhenAddingMoreThanMaxLimitOfThirtyModules() {
        NotificadorStub notificador = new NotificadorStub();
        Course curso = new Course("React Básico", "Descripción", notificador);

        for (int i = 0; i < 30; i++) {
            curso.agregarModulo(new Module("Módulo " + i));
        }

        assertThrows(IllegalStateException.class, () -> {
            curso.agregarModulo(new Module("Módulo 31"));
        });
    }

    @Test // Debe permitir modificar el título y la descripción si el curso está en BORRADOR
    void shouldAllowModifyingTitleAndDescriptionWhenCourseIsDraft() {
        NotificadorStub notificador = new NotificadorStub();
        Course curso = new Course("React Básico", "Descripción", notificador);

        curso.actualizarInformacion("React Avanzado", "Nueva descripción");

        assertEquals("React Avanzado", curso.getTitulo());
        assertEquals("Nueva descripción", curso.getDescripcion());
    }

    @Test // Debe lanzar excepción al intentar modificar el título o la descripción si el curso ya está PUBLICADO
    void shouldThrowExceptionWhenModifyingTitleOrDescriptionOnPublishedCourse() {
        NotificadorStub notificador = new NotificadorStub();
        Course curso = new Course("React Básico", "Descripción", notificador);
        curso.agregarModulo(new Module("Módulo 1"));
        curso.publicar();

        assertThrows(IllegalStateException.class, () -> {
            curso.actualizarInformacion("React Avanzado", "Nueva descripción");
        });
    }

    @Test // Debe lanzar excepción al intentar eliminar un módulo si el curso ya está PUBLICADO
    void shouldThrowExceptionWhenRemovingModuleFromPublishedCourse() {
        NotificadorStub notificador = new NotificadorStub();
        Course curso = new Course("React Básico", "Descripción", notificador);
        Module modulo = new Module("Módulo 1");
        curso.agregarModulo(modulo);
        curso.publicar();

        assertThrows(IllegalStateException.class, () -> {
            curso.removerModulo(modulo);
        });
    }
}