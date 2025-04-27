package com.databaseproject.cinedev.stages;

import com.databaseproject.cinedev.CinedevApplication;
import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.services.base.user.UserService;
import com.databaseproject.cinedev.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginPage implements IWindowScene {
    UserService userService;

    public LoginPage() {
        this.userService = CinedevApplication.getSpringContext().getBean(UserService.class);
    }

    @Override
    public Scene showWindow(Stage primaryStage) {
        StackPane form = loginForm(primaryStage);
        StackPane logo = Utils.logoCine(200);

        VBox root = new VBox(logo, form);
        root.setBackground(Utils.backgroundImageWindows());
        root.setSpacing(10);

        return new Scene(root, 600, 600);
    }

    private StackPane loginForm(Stage primaryStage) {
        Rectangle rectangle = new Rectangle(250, 250);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);

        Label title = new Label("Log In".toUpperCase());
        title.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20px;" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Arial';"
        );
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setSpacing(2);

        Label emailLabel = new Label();
        emailLabel.setText("Email: ");
        emailLabel.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Arial';"
        );
        TextField emailField = new TextField();
        emailField.setPromptText("Ej: hola@gmail.com");
        emailField.setMaxWidth(Double.MAX_VALUE);

        Label passwordLabel = new Label();
        passwordLabel.setText("Password: ");
        passwordLabel.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Arial';"
        );
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("**********");
        passwordField.setMaxWidth(Double.MAX_VALUE);

        Button button = new Button("Log In");
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        button.setMaxWidth(Double.MAX_VALUE);

        Label registerLabel = new Label();
        registerLabel.setText("Don't have an account? Register here");
        registerLabel.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");

        button.setOnAction(e -> {
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

            User userFromDb = userService.getUserByEmail(email);
            if (userFromDb == null) {
                Utils.sendMessage("User not found. Please check your information or register one account", Alert.AlertType.ERROR);
                return;
            }

            User existingUser = userService.getUserWithRolesById(userFromDb.getId());
            if (existingUser.isPasswordCheck(password, existingUser.getPassword())) {

                Utils.sendMessage("Login successful! Welcome.", Alert.AlertType.INFORMATION);
                System.out.println("User logged in: " + existingUser.getFullName());
                existingUser.getUserRoles().forEach(r -> System.out.println("Role: " + r.getRoles().getName()));

                Utils.loadWindowsToShow(new MainPage(existingUser), primaryStage);
            } else {
                Utils.sendMessage("Incorrect password. Try again.", Alert.AlertType.ERROR);
                passwordField.setStyle("-fx-border-color: red;");
            }
        });

        Utils.removeErrorStyleOnTyping(emailField);
        Utils.removeErrorStyleOnTyping(passwordField);

        registerLabel.setOnMouseClicked(e -> {
            Utils.loadWindowsToShow(new RegisterPage(), primaryStage);
        });

        VBox form = new VBox(10, titleBox, emailLabel, emailField, passwordLabel, passwordField, button, registerLabel);
        form.setPadding(new Insets(20));
        form.setMaxWidth(360);
        form.setFillWidth(true);

        return new StackPane(form);
    }
}
