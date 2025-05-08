package com.databaseproject.cinedev.presentation.controller;

import com.databaseproject.cinedev.CinedevApplication;
import com.databaseproject.cinedev.application.services.user.UserService;
import com.databaseproject.cinedev.application.utils.Utils;
import com.databaseproject.cinedev.domain.models.base.User;
import com.databaseproject.cinedev.presentation.PathViews;
import com.databaseproject.cinedev.presentation.ViewSwitcher;
import com.databaseproject.cinedev.presentation.services.LoginPageService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class LoginController {
    @FXML
    private StackPane logoPane;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label registerLabel;

    private final UserService userService = CinedevApplication.getSpringContext().getBean(UserService.class);

    private LoginPageService loginPageService;

    @FXML
    private void initialize() {
        Utils.removeErrorStyleOnTyping(emailField);
        Utils.removeErrorStyleOnTyping(passwordField);

        logoPane.getChildren().add(Utils.logoCine(200));

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                emailField.setStyle("-fx-border-color: red;");
                passwordField.setStyle("-fx-border-color: red;");
                Utils.sendMessage("Ops! Please complete all fields!", Alert.AlertType.WARNING);
                return;
            }

            if(!Utils.isEmailValidAndRecognized(email)) {
                return;
            }

            User existingUser = loginPageService.login(email, password);
            if (existingUser != null) {
                Stage taskView = (Stage) loginButton.getScene().getWindow();
                if (taskView == null) {
                    Utils.sendMessage("Internal error: scene not loaded.", Alert.AlertType.ERROR);
                    return;
                }

                Utils.sendMessage("Login successful! Welcome.", Alert.AlertType.INFORMATION);
                ViewSwitcher.switchTo(taskView, PathViews.TASK.getPathViews(), "Home", 1280, 720);
            } else {
                Utils.sendMessage("Incorrect password. Try again.", Alert.AlertType.ERROR);
                passwordField.setStyle("-fx-border-color: red;");
            }
        });

        registerLabel.setOnMouseClicked(e -> {
            Stage registerView = (Stage) registerLabel.getScene().getWindow();
            if (registerView == null) {
                Utils.sendMessage("Internal error: scene not loaded.", Alert.AlertType.ERROR);
                return;
            }

            ViewSwitcher.switchTo(registerView, PathViews.REGISTER.getPathViews(), "Home", 1280, 720);
        });
    }
}
