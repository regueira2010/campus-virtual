package com.campusvirtual;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Module {
    private String title;
    private List<Content> contents;

    public Module(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidModuleTitleException("Module title cannot be empty");
        }
        this.title = title;
        this.contents = new ArrayList<>();
    }

    public void addContent(Content content) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        this.contents.add(content);
    }

    public void removeContent(Content content) {
        this.contents.remove(content);
    }

    public String getTitle() {
        return title;
    }

    public List<Content> getContents() {
        return Collections.unmodifiableList(contents);
    }
}