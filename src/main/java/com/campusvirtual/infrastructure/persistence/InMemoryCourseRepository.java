package com.campusvirtual.infrastructure.persistence;

import com.campusvirtual.domain.entity.Course;
import com.campusvirtual.domain.repository.CourseRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCourseRepository implements CourseRepository {

    private final Map<String, Course> courses = new HashMap<>();

    @Override
    public Course save(Course course) {
        courses.put(course.getTitle(), course);
        return course;
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(courses.get(title));
    }
}
