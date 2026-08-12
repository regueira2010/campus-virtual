package com.campusvirtual.infrastructure.persistence;

import com.campusvirtual.domain.entity.Course;
import com.campusvirtual.domain.entity.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCourseRepositoryTest {

    private InMemoryCourseRepository repository;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new InMemoryCourseRepository();
    }

    @Test
    @DisplayName("Should save and find course by title successfully")
    void shouldSaveAndFindCourse() {
        Course course = new Course("Java Clean Architecture", "Description", notificationService);
        
        repository.save(course);
        Optional<Course> found = repository.findByTitle("Java Clean Architecture");

        assertTrue(found.isPresent());
        assertEquals("Java Clean Architecture", found.get().getTitle());
    }

    @Test
    @DisplayName("Should return empty optional when course title is not found")
    void shouldReturnEmptyWhenNotFound() {
        Optional<Course> found = repository.findByTitle("Non Existent Course");
        assertFalse(found.isPresent());
    }
}
