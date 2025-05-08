package com.databaseproject.cinedev.presentation.controller;

import com.databaseproject.cinedev.CinedevApplication;
import com.databaseproject.cinedev.application.utils.Utils;
import com.databaseproject.cinedev.domain.enums.RoleUser;
import com.databaseproject.cinedev.domain.models.base.User;
import com.databaseproject.cinedev.presentation.PathViews;
import com.databaseproject.cinedev.presentation.ViewSwitcher;
import com.databaseproject.cinedev.presentation.services.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class RegisterController {
    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label goToLoginLabel;

    private final AuthService authService = CinedevApplication.getSpringContext().getBean(AuthService.class);

    @FXML
    public void initialize() {
        Utils.removeErrorStyleOnTyping(fullNameField);
        Utils.removeErrorStyleOnTyping(emailField);
        Utils.removeErrorStyleOnTyping(passwordField);
        Utils.removeErrorStyleOnTyping(repeatPasswordField);

        registerButton.setOnAction(e -> {
            String name = fullNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String repeatPassword = repeatPasswordField.getText();

            if (name.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                fullNameField.setStyle("-fx-border-color: red;");
                emailField.setStyle("-fx-border-color: red;");
                passwordField.setStyle("-fx-border-color: red;");
                repeatPasswordField.setStyle("-fx-border-color: red;");
                Utils.sendMessage("Ops! Please complete all fields!", Alert.AlertType.WARNING);
                return;
            }

            String typeEmail = Utils.recognizeTypeOfEmail(email);
            if (typeEmail.equals("invalid")) {
                emailField.setStyle("-fx-border-color: red;");
                Utils.sendMessage("Type of Email wrong. Try with other.", Alert.AlertType.ERROR);
                return;
            }

            if (typeEmail.equals("unknown")) {
                emailField.setStyle("-fx-border-color: red;");
                Utils.sendMessage("Unrecognized email domain. Only Gmail, Outlook or Hotmail is allowed.", Alert.AlertType.WARNING);
                return;
            }

            if (!password.equals(repeatPassword)) {
                passwordField.setStyle("-fx-border-color: red;");
                repeatPasswordField.setStyle("-fx-border-color: red;");
                Utils.sendMessage("Your password not equals. Try again", Alert.AlertType.ERROR);
                return;
            }


            User user = new User(name, email, password, RoleUser.USERS);
            if (!authService.register(user)) {
                Utils.sendMessage("Email already in use. Try another one.", Alert.AlertType.INFORMATION);
                return;
            }

            authService.register(user);

            cleanFields(fullNameField, emailField, passwordField, repeatPasswordField);

            Utils.sendMessage("¡Successfully Register! Welcome, " + user.getFullName() + "! Now you can log in with your account.", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) registerButton.getScene().getWindow();
            ViewSwitcher.switchTo(stage, PathViews.LOGIN.getPathViews(), "Login", 600, 600);
        });

        goToLoginLabel.setOnMouseClicked(e -> {
            Stage stage = (Stage) goToLoginLabel.getScene().getWindow();
            ViewSwitcher.switchTo(stage, PathViews.LOGIN.getPathViews(), "Login", 600, 600);
        });
    }

    private static void cleanFields(TextField fullNameField, TextField nameField, PasswordField passwordField, PasswordField repeatPasswordField) {
        fullNameField.clear();
        nameField.clear();
        passwordField.clear();
        repeatPasswordField.clear();
    }
}
