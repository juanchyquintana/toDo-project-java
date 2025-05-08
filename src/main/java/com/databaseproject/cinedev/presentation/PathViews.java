package com.databaseproject.cinedev.presentation;

import lombok.Getter;

@Getter
public enum PathViews {
    LOGIN("/views/login-page.fxml"),
    REGISTER("/views/register-page.fxml"),
    TASK("/views/task-page.fxml"),
    USER("/views/user-page.fxml");

    private final String pathViews;

    PathViews(String pathViews) {
        this.pathViews = pathViews;
    }
}
