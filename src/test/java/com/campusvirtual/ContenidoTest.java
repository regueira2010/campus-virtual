package com.campusvirtual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ContenidoTest {

    @Test // Debe crear un contenido correctamente con un id único y los datos provistos
    void shouldCreateContenidoSuccessfully() {
        // Arrange
        String titulo = "Introducción a React";
        int orden = 1;

        // Act
        Content contenido = new Content(titulo, orden);

        // Assert
        assertNotNull(contenido.getId(), "El ID del contenido no debe ser nulo");
        assertFalse(contenido.getId().isEmpty(), "El ID del contenido no debe estar vacío");
        assertEquals(titulo, contenido.getTitulo(), "El título debe coincidir");
        assertEquals(orden, contenido.getOrden(), "El orden de secuencia debe coincidir");
    }

    @ParameterizedTest // Debe lanzar excepción cuando el título del contenido es inválido o está vacío
    @ValueSource(strings = { "", "   ", "  " })
    void shouldThrowExceptionWhenTituloIsInvalid(String tituloInvalido) {
        // Act & Assert
        assertThrows(TituloContenidoInvalidoException.class, () -> {
            new Content(tituloInvalido, 1);
        });
    }

    @Test // Debe lanzar excepción si el título es nulo
    void shouldThrowExceptionWhenTituloIsNull() {
        // Act & Assert
        assertThrows(TituloContenidoInvalidoException.class, () -> {
            new Content(null, 1);
        });
    }

    @Test // Debe lanzar excepción si el orden asignado es menor que 1
    void shouldThrowExceptionWhenOrdenIsLessThanOne() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Content("Clase 1", 0);
        });
    }
}