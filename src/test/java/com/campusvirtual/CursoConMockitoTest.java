package com.campusvirtual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CursoConMockitoTest {

    @Mock
    private NotificationService notificadorMock;

    @Test
    void shouldSendNotificationWhenCourseIsCreated() {
        // Arrange
        String titulo = "Certificación React";
        String descripcion = "Curso completo de React desde cero";
        String destinatarioEsperado = "admin@campus.com";
        String mensajeEsperado = "Curso creado: " + titulo;

        // Act
        Course curso = new Course(titulo, descripcion, notificadorMock);

        // Assert - verificar que el notificador fue llamado exactamente una vez
        verify(notificadorMock, times(1)).enviar(destinatarioEsperado, mensajeEsperado);
    }
}