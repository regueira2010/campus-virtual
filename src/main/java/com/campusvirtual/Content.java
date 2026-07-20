package com.campusvirtual;

import java.util.UUID;

public class Content {
    private final String id;
    private final String title;
    private final int order; // Sequential position within the course (e.g., 1, 2, 3...)

    public Content(String title, int order) {
        validateTitle(title);
        validateOrder(order);
        this.id = UUID.randomUUID().toString();
        this.title = title.trim();
        this.order = order;
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidContentTitleException("Content title cannot be empty.");
        }
    }

    private void validateOrder(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("Content order must be greater than or equal to 1.");
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getOrder() {
        return order;
    }
}