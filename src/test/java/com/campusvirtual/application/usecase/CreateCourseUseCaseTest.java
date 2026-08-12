package com.campusvirtual.application.usecase;

import com.campusvirtual.domain.entity.Course;
import com.campusvirtual.domain.entity.NotificationService;
import com.campusvirtual.domain.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCourseUseCaseTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private CreateCourseUseCase createCourseUseCase;

    @Test
    void shouldCreateCourseSuccessfully() {
        when(courseRepository.findByTitle("Java Clean Architecture")).thenReturn(Optional.empty());
        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Course createdCourse = createCourseUseCase.execute(
            "Java Clean Architecture",
            "Curso sobre DDD y Arquitectura Limpia",
            notificationService
        );

        assertNotNull(createdCourse);
        assertEquals("Java Clean Architecture", createdCourse.getTitle());
        verify(courseRepository, times(1)).save(any(Course.class));
    }
}