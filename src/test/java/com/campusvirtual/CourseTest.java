package com.campusvirtual;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should create course successfully with default values and notify admin")
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
    @DisplayName("Should throw InvalidTitleException when course title is empty or blank on creation")
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
    @DisplayName("Should throw IllegalStateException when publishing course without modules")
    void shouldThrowExceptionWhenPublishingCourseWithoutAnyModules() {
        // Arrange
        Course course = new Course("Basic React", "Description", notificationService);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            course.publish();
        });
    }

    @Test
    @DisplayName("Should change status to PUBLISHED when course is published with at least one module")
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
    @DisplayName("Should throw IllegalStateException when exceeding maximum limit of thirty modules")
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
    @DisplayName("Should allow updating title and description when course status is DRAFT")
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
    @DisplayName("Should throw IllegalStateException when updating title or description on a published course")
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
    @DisplayName("Should throw IllegalStateException when removing module from a published course")
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

    @Test
    @DisplayName("Should throw InvalidTitleException when course title is null on creation")
    void shouldThrowExceptionWhenCourseTitleIsNullOnCreation() {
        // Line 15 coverage: null title
        assertThrows(InvalidTitleException.class, () -> {
            new Course(null, "Description", notificationService);
        });
    }

    @Test
    @DisplayName("Should remove module successfully when course status is DRAFT")
    void shouldRemoveModuleSuccessfullyWhenCourseIsDraft() {
        // Lines 36-39 coverage: successfully removing a module in DRAFT status
        Course course = new Course("Basic React", "Description", notificationService);
        Module module = new Module("Module 1");
        course.addModule(module);

        course.removeModule(module);

        assertTrue(course.getModules().isEmpty(), "Module list should be empty after removal");
    }

    @ParameterizedTest
    @DisplayName("Should throw InvalidTitleException when course title is empty or blank on update")
    @ValueSource(strings = { "", "   ", "  " })
    void shouldThrowExceptionWhenUpdatingWithInvalidTitle(String invalidTitle) {
        // Lines 53-54 coverage: updating with blank title
        Course course = new Course("Basic React", "Description", notificationService);

        assertThrows(InvalidTitleException.class, () -> {
            course.updateInformation(invalidTitle, "New Description");
        });
    }

    @Test
    @DisplayName("Should throw InvalidTitleException when course title is null on update")
    void shouldThrowExceptionWhenUpdatingWithNullTitle() {
        // Lines 53-54 coverage: updating with null title
        Course course = new Course("Basic React", "Description", notificationService);

        assertThrows(InvalidTitleException.class, () -> {
            course.updateInformation(null, "New Description");
        });
    }
}