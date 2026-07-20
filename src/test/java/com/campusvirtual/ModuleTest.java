package com.campusvirtual;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ModuleTest {

    @Test
    @DisplayName("Should create module successfully with a valid title")
    void shouldCreateModuleSuccessfully() {
        // Arrange
        String title = "Introduction to Spring";

        // Act
        Module module = new Module(title);

        // Assert
        assertEquals(title, module.getTitle());
        assertTrue(module.getContents().isEmpty());
    }

    @ParameterizedTest
    @DisplayName("Should throw InvalidModuleTitleException when module title is empty or blank")
    @ValueSource(strings = { "", "   ", "  " })
    void shouldThrowExceptionWhenTitleIsInvalid(String invalidTitle) {
        assertThrows(InvalidModuleTitleException.class, () -> new Module(invalidTitle));
    }

    @Test
    @DisplayName("Should add and remove content successfully from module")
    void shouldAddAndRemoveContentSuccessfully() {
        // Arrange
        Module module = new Module("Module 1");
        Content content = new Content("Lesson 1", 1);

        // Act
        module.addContent(content);

        // Assert
        assertEquals(1, module.getContents().size());
        assertTrue(module.getContents().contains(content));

        // Act
        module.removeContent(content);

        // Assert
        assertTrue(module.getContents().isEmpty());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding null content to module")
    void shouldThrowExceptionWhenAddingNullContent() {
        Module module = new Module("Module 1");
        assertThrows(IllegalArgumentException.class, () -> module.addContent(null));
    }

    @Test
    @DisplayName("Should throw InvalidModuleTitleException when module title is null")
    void shouldThrowExceptionWhenTitleIsNull() {
        assertThrows(InvalidModuleTitleException.class, () -> new Module(null));
    }
}
