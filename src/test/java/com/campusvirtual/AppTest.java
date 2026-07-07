package com.campusvirtual;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    void testSumar() {
        // Arrange: preparar los datos
        Calculadora calc = new Calculadora();
        int a = 2;
        int b = 3;

        // Act: ejecutar el método
        int resultado = calc.sumar(a, b);

        // Assert: verificar el resultado
        assertEquals(5, resultado, "2 + 3 debería ser 5");
    }
}
