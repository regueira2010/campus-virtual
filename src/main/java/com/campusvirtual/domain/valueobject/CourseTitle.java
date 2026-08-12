package com.campusvirtual.domain.valueobject;

import com.campusvirtual.domain.exception.InvalidTitleException;

public record CourseTitle(String value) {
    public CourseTitle {
        if (value == null || value.isBlank()) {
            throw new InvalidTitleException("El título del curso no puede estar vacío.");
        }
        value = value.trim();
    }
}