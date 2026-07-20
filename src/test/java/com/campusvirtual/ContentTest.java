package com.campusvirtual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ContentTest {

    @Test
    void shouldCreateContentSuccessfully() {
        // Arrange
        String title = "Introduction to React";
        int order = 1;

        // Act
        Content content = new Content(title, order);

        // Assert
        assertNotNull(content.getId(), "Content ID should not be null");
        assertFalse(content.getId().isEmpty(), "Content ID should not be empty");
        assertEquals(title, content.getTitle(), "Title should match");
        assertEquals(order, content.getOrder(), "Sequence order should match");
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "   ", "  " })
    void shouldThrowExceptionWhenTitleIsInvalid(String invalidTitle) {
        // Act & Assert
        assertThrows(InvalidContentTitleException.class, () -> {
            new Content(invalidTitle, 1);
        });
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        // Act & Assert
        assertThrows(InvalidContentTitleException.class, () -> {
            new Content(null, 1);
        });
    }

    @Test
    void shouldThrowExceptionWhenOrderIsLessThanOne() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Content("Lesson 1", 0);
        });
    }
}