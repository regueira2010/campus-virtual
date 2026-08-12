package com.campusvirtual.domain.entity;

public interface NotificationService {
    void send(String recipient, String message);
}