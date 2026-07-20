package com.campusvirtual;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class CourseTest {

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCourseWithDefaultValues() {
        // Arrange
        String title = "React Certification";
        String description = "Complete React course from scratch";

        // Act
        Course course = new Course(title, description, notificationService);

        // Assert - Course state
        assertNotNull(course, "Course should not be null");
        assertEquals(title, course.getTitle(), "Title should match");
        assertEquals(description, course.getDescription(), "Description should match");
        assertEquals("DRAFT", course.getStatus(), "Initial status should be DRAFT");
        assertTrue(course.getModules().isEmpty(), "Module list should be empty");

        // Assert - Verify mock call
        verify(notificationService).send("admin@campus.com", "Course created: " + title);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "   ", "  " })
    void shouldThrowExceptionWhenTitleIsInvalid(String invalidTitle) {
        // Arrange
        String description = "Valid description";

        // Act & Assert
        assertThrows(InvalidTitleException.class, () -> {
            new Course(invalidTitle, description, notificationService);
        });
    }

    @Test
    void shouldThrowExceptionWhenPublishingCourseWithoutAnyModules() {
        // Arrange
        Course course = new Course("Basic React", "Description", notificationService);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            course.publish();
        });
    }

    @Test
    void shouldChangeStatusToPublishedWhenCourseHasAtLeastOneModule() {
        // Arrange
        Course course = new Course("Basic React", "Description", notificationService);
        Module module = new Module("Introduction to React");

        // Act
        course.addModule(module);
        course.publish();

        // Assert
        assertEquals("PUBLISHED", course.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenAddingMoreThanMaxLimitOfThirtyModules() {
        // Arrange
        Course course = new Course("Basic React", "Description", notificationService);

        for (int i = 0; i < 30; i++) {
            course.addModule(new Module("Module " + i));
        }

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            course.addModule(new Module("Module 31"));
        });
    }

    @Test
    void shouldAllowModifyingTitleAndDescriptionWhenCourseIsDraft() {
        // Arrange
        Course course = new Course("Basic React", "Description", notificationService);

        // Act
        course.updateInformation("Advanced React", "New description");

        // Assert
        assertEquals("Advanced React", course.getTitle());
        assertEquals("New description", course.getDescription());
    }

    @Test
    void shouldThrowExceptionWhenModifyingTitleOrDescriptionOnPublishedCourse() {
        // Arrange
        Course course = new Course("Basic React", "Description", notificationService);
        course.addModule(new Module("Module 1"));
        course.publish();

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            course.updateInformation("Advanced React", "New description");
        });
    }

    @Test
    void shouldThrowExceptionWhenRemovingModuleFromPublishedCourse() {
        // Arrange
        Course course = new Course("Basic React", "Description", notificationService);
        Module module = new Module("Module 1");
        course.addModule(module);
        course.publish();

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            course.removeModule(module);
        });
    }
}