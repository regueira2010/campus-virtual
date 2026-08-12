package com.campusvirtual.domain.entity;

import com.campusvirtual.domain.valueobject.CourseTitle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course {
    private CourseTitle title;
    private String description;
    private String status;
    private List<Module> modules;
    private NotificationService notificationService;

    public Course(String title, String description, NotificationService notificationService) {
        this.title = new CourseTitle(title);
        this.description = description;
        this.status = "DRAFT";
        this.modules = new ArrayList<>();
        this.notificationService = notificationService;

        this.notificationService.send("admin@campus.com", "Course created: " + this.title.value());
    }

    public void addModule(Module module) {
        if (this.modules.size() >= 30) {
            throw new IllegalStateException("Course cannot exceed 30 modules");
        }
        this.modules.add(module);
    }

    public void removeModule(Module module) {
        if ("PUBLISHED".equals(this.status)) {
            throw new IllegalStateException("Cannot remove modules from a published course");
        }
        this.modules.remove(module);
    }

    public void publish() {
        if (this.modules.isEmpty()) {
            throw new IllegalStateException("Cannot publish a course without modules");
        }
        this.status = "PUBLISHED";
    }

    public void updateInformation(String newTitle, String newDescription) {
        if (!"DRAFT".equals(this.status)) {
            throw new IllegalStateException("Course information can only be updated in DRAFT status");
        }
        this.title = new CourseTitle(newTitle);
        this.description = newDescription;
    }

    public String getTitle() {
        return title.value();
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public List<Module> getModules() {
        return Collections.unmodifiableList(modules);
    }
}