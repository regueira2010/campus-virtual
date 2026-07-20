package com.campusvirtual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Progress {
    private final String studentId;
    private final Course course;
    private final List<String> completedContentIds; // Stores the IDs of completed contents

    public Progress(String studentId, Course course) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        this.studentId = studentId;
        this.course = course;
        this.completedContentIds = new ArrayList<>();
    }

    // --- KEY BUSINESS RULE: COMPLETE CONTENT SEQUENTIALLY ---
    public void completeContent(Content contentToRecord) {
        // If already completed, ignore safely without duplicating or throwing an error
        if (completedContentIds.contains(contentToRecord.getId())) {
            return;
        }

        // Collect all contents across all modules in the course
        List<Content> allContents = getAllCourseContents();

        // Validate if there is any previous content that is still pending
        for (Content evaluatedContent : allContents) {
            if (evaluatedContent.getOrder() < contentToRecord.getOrder()) {
                if (!completedContentIds.contains(evaluatedContent.getId())) {
                    throw new IllegalStateException("Cannot complete this content because previous lessons are pending.");
                }
            }
        }

        // If validation passed (or it's the first content), mark as completed
        this.completedContentIds.add(contentToRecord.getId());
    }

    // --- BUSINESS RULE: CALCULATE PROGRESS PERCENTAGE ---
    public double calculateProgressPercentage() {
        List<Content> allContents = getAllCourseContents();
        if (allContents.isEmpty()) {
            return 0.0;
        }

        double completed = completedContentIds.size();
        double total = allContents.size();

        return (completed / total) * 100.0;
    }

    // Private helper method to extract all contents
    private List<Content> getAllCourseContents() {
        List<Content> unifiedList = new ArrayList<>();
        for (Module module : course.getModules()) {
            unifiedList.addAll(module.getContents());
        }
        return unifiedList;
    }

    // --- GETTERS ---
    public String getStudentId() {
        return studentId;
    }

    public Course getCourse() {
        return course;
    }

    public List<String> getCompletedContentIds() {
        return Collections.unmodifiableList(completedContentIds);
    }
}