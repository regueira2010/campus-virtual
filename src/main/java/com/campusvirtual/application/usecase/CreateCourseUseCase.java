package com.campusvirtual.application.usecase;

import com.campusvirtual.domain.entity.Course;
import com.campusvirtual.domain.entity.NotificationService;
import com.campusvirtual.domain.repository.CourseRepository;
import com.campusvirtual.domain.valueobject.CourseTitle;

public class CreateCourseUseCase {

    private final CourseRepository courseRepository;

    public CreateCourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course execute(String rawTitle, String description, NotificationService notificationService) {
        CourseTitle title = new CourseTitle(rawTitle);
        
        courseRepository.findByTitle(title.value()).ifPresent(existingCourse -> {
            throw new IllegalStateException("Ya existe un curso registrado con el título: " + rawTitle);
        });

        Course course = new Course(title.value(), description, notificationService);
        return courseRepository.save(course);
    }
}
