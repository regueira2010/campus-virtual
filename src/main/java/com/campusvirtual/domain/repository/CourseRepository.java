package com.campusvirtual.domain.repository;

import com.campusvirtual.domain.entity.Course;
import java.util.Optional;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findByTitle(String title);
}