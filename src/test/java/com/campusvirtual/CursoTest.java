package com.campusvirtual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CursoTest {

    @Test
    void shouldCreateCourseWithDefaultValues() {
        // Arrange
        NotificadorStub notificador = new NotificadorStub();
        String titulo = "Certificación React";
        String descripcion = "Curso completo de React desde cero";

        // Act
        Curso curso = new Curso(titulo, descripcion, notificador);

        // Assert - estado del curso
        assertNotNull(curso, "El curso no debería ser nulo");
        assertEquals(titulo, curso.getTitulo(), "El título debería coincidir");
        assertEquals(descripcion, curso.getDescripcion(), "La descripción debería coincidir");
        assertEquals("EN_BORRADOR", curso.getEstado(), "El estado inicial debería ser EN_BORRADOR");
        assertTrue(curso.getModulos().isEmpty(), "La lista de módulos debería estar vacía");

        // Assert - verificar que el notificador fue llamado correctamente
        assertTrue(notificador.fueLlamado(), "El notificador debería haber sido llamado");
        assertEquals("admin@campus.com", notificador.getUltimoDestinatario(), "El destinatario debería ser admin@campus.com");
        assertEquals("Curso creado: " + titulo, notificador.getUltimoMensaje(), "El mensaje debería coincidir");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "  "})
    void shouldThrowExceptionWhenTituloIsInvalid(String tituloInvalido) {
        // Arrange
        NotificadorStub notificador = new NotificadorStub();
        String descripcion = "Descripción válida";

        // Act & Assert
        assertThrows(TituloInvalidoException.class, () -> {
            new Curso(tituloInvalido, descripcion, notificador);
        });
    }
}