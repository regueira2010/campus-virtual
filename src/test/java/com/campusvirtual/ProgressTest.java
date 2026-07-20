package com.campusvirtual;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class ProgressTest {

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should initialize student progress at 0% and empty completed list upon enrollment")
    void shouldInitializeWithZeroPercentProgressAndEmptyCompletedListWhenStudentEnrolls() {
        // Arrange
        Course course = createCourse("Advanced Java");
        String studentId = "student-123";

        // Act
        Progress progress = new Progress(studentId, course);

        // Assert
        assertEquals(studentId, progress.getStudentId());
        assertEquals(course, progress.getCourse());
        assertTrue(progress.getCompletedContentIds().isEmpty(), "The completed content list should start empty");
        assertEquals(0.0, progress.calculateProgressPercentage(), "The initial progress percentage should be 0%");
    }

    @Test
    @DisplayName("Should allow completing the first content directly without any prerequisites")
    void shouldAllowCompletingTheFirstContentDirectlyWithoutAnyPredecessors() {
        // Arrange
        Course course = createCourse("Advanced Java");
        Module module = new Module("Introduction");
        Content content1 = new Content("Variables", 1);

        module.addContent(content1);
        course.addModule(module);

        Progress progress = new Progress("student-123", course);

        // Act
        progress.completeContent(content1);

        // Assert
        assertTrue(progress.getCompletedContentIds().contains(content1.getId()), "The first content should be marked as completed");
        assertEquals(100.0, progress.calculateProgressPercentage(), "Progress should reach 100% after completing the only lesson");
    }

    @Test
    @DisplayName("Should throw IllegalStateException when attempting to complete content with pending previous lessons")
    void shouldThrowIllegalStateExceptionWhenAttemptingToCompleteContentWithPendingPredecessors() {
        // Arrange
        Course course = createCourse("Advanced Java");
        Module module = new Module("Introduction");
        Content content1 = new Content("Variables", 1);
        Content content2 = new Content("Loops", 2);

        module.addContent(content1);
        module.addContent(content2);
        course.addModule(module);

        Progress progress = new Progress("student-123", course);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            progress.completeContent(content2);
        });

        assertEquals("Cannot complete this content because previous lessons are pending.", exception.getMessage());
        assertFalse(progress.getCompletedContentIds().contains(content2.getId()), "Content 2 should not be marked as completed");
    }

    @Test
    @DisplayName("Should complete content successfully when all previous contents are completed")
    void shouldAllowCompletingContentSuccessfullyWhenAllPreviousContentsAreAlreadyCompleted() {
        // Arrange
        Course course = createCourse("Advanced Java");
        Module module = new Module("Introduction");
        Content content1 = new Content("Variables", 1);
        Content content2 = new Content("Loops", 2);

        module.addContent(content1);
        module.addContent(content2);
        course.addModule(module);

        Progress progress = new Progress("student-123", course);

        // Act
        progress.completeContent(content1); 
        progress.completeContent(content2); 

        // Assert
        assertTrue(progress.getCompletedContentIds().contains(content2.getId()), "Content 2 should be completed successfully");
        assertEquals(100.0, progress.calculateProgressPercentage(), "Both lessons completed equal 100%");
    }

    @Test
    @DisplayName("Should ignore completion request if the content was already completed")
    void shouldIgnoreRequestAndNotDuplicateEntryWhenContentIsAlreadyMarkedAsCompleted() {
        // Arrange
        Course course = createCourse("Advanced Java");
        Module module = new Module("Introduction");
        Content content1 = new Content("Variables", 1);

        module.addContent(content1);
        course.addModule(module);

        Progress progress = new Progress("student-123", course);

        // Act
        progress.completeContent(content1);
        progress.completeContent(content1); 

        // Assert
        assertEquals(1, progress.getCompletedContentIds().size(), "ID should not be duplicated in the list");
        assertEquals(100.0, progress.calculateProgressPercentage());
    }

    @Test
    @DisplayName("Should calculate accurate progress percentage based on total completed contents")
    void shouldCalculateAccurateProgressPercentageBasedOnTotalCompletedContents() {
        // Arrange
        Course course = createCourse("Advanced Java");
        Module module = new Module("Introduction");
        Content content1 = new Content("Variables", 1);
        Content content2 = new Content("Loops", 2);
        Content content3 = new Content("Methods", 3);
        Content content4 = new Content("Classes", 4);

        module.addContent(content1);
        module.addContent(content2);
        module.addContent(content3);
        module.addContent(content4);
        course.addModule(module);

        Progress progress = new Progress("student-123", course);

        // Act
        progress.completeContent(content1);
        progress.completeContent(content2);
        // Assert
        assertEquals(50.0, progress.calculateProgressPercentage(), "Calculated progress should be exactly 50%");
    }

    @Test
    @DisplayName("Should return exactly 0% progress when the course has no contents")
    void shouldReturnZeroPercentProgressWhenCourseDoesNotContainAnyContents() {
        // Arrange
        Course course = createCourse("Empty Java");
        Progress progress = new Progress("student-123", course);

        // Act
        double percentage = progress.calculateProgressPercentage();

        // Assert
        assertEquals(0.0, percentage, "Avoids division by zero and safely returns 0.0");
    }

    private Course createCourse(String title) {
        return new Course(title, "Description", notificationService);
    }

    @ParameterizedTest
    @DisplayName("Should throw IllegalArgumentException when student ID is empty or blank")
    @org.junit.jupiter.params.provider.ValueSource(strings = { "", "   ", "  " })
    void shouldThrowExceptionWhenStudentIdIsInvalid(String invalidStudentId) {
        // Lines 13-14 coverage: invalid studentId
        Course course = createCourse("Java Course");
        assertThrows(IllegalArgumentException.class, () -> {
            new Progress(invalidStudentId, course);
        });
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when student ID is null")
    void shouldThrowExceptionWhenStudentIdIsNull() {
        // Lines 13-14 coverage: null studentId
        Course course = createCourse("Java Course");
        assertThrows(IllegalArgumentException.class, () -> {
            new Progress(null, course);
        });
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when course is null")
    void shouldThrowExceptionWhenCourseIsNull() {
        // Lines 16-17 coverage: null course
        assertThrows(IllegalArgumentException.class, () -> {
            new Progress("student-123", null);
        });
    }
}