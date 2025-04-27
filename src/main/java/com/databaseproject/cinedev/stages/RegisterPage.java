package com.databaseproject.cinedev.stages;

import com.databaseproject.cinedev.CinedevApplication;
import com.databaseproject.cinedev.models.base.Roles;
import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.services.base.roles.RoleService;
import com.databaseproject.cinedev.services.base.user.UserService;
import com.databaseproject.cinedev.services.base.userRole.UserRoleService;
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

public class RegisterPage implements IWindowScene {
    UserService userService;
    UserRoleService userRoleService;
    RoleService roleService;

    public RegisterPage() {
        this.userService = CinedevApplication.getSpringContext().getBean(UserService.class);
        this.userRoleService = CinedevApplication.getSpringContext().getBean(UserRoleService.class);
        this.roleService = CinedevApplication.getSpringContext().getBean(RoleService.class);
    }

    @Override
    public Scene showWindow(Stage primaryStage) {
        StackPane form = formRegister(primaryStage);
        StackPane logo = Utils.logoCine(200);

        VBox root = new VBox(logo, form);
        root.setBackground(Utils.backgroundImageWindows());
        root.setSpacing(10);

        return new Scene(root, 600, 600);
    }

    private StackPane formRegister(Stage primaryStage) {
        Rectangle rectangle = new Rectangle(250, 250);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);

        Label title = new Label("Create your Account".toUpperCase());
        title.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20px;" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Arial';"
        );

        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setSpacing(5);

        Label fullName = new Label();
        fullName.setText("Full Name: ");
        fullName.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Arial';"
        );
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Ej: Pedro Miguel Savio");
        fullNameField.setMaxWidth(Double.MAX_VALUE);

        Label nameLabel = new Label();
        nameLabel.setText("Email: ");
        nameLabel.setStyle(
                "-fx-font-weight: bold;" +
                    "-fx-font-size: 12px;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-family: 'Arial';"
        );
        TextField emailField = new TextField();
        emailField.setPromptText("hola@gmail.com");
        emailField.setStyle(
                "-fx-font-weight: bold;" +
                    "-fx-font-size: 12px;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-family: 'Arial';"
        );
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

        Label repeatPasswordLabel = new Label();
        repeatPasswordLabel.setText("Repeat your Password: ");
        repeatPasswordLabel.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Arial';"
        );
        PasswordField repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("**********");
        repeatPasswordField.setMaxWidth(Double.MAX_VALUE);

        Button button = new Button("Create Account");
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx");
        button.setMaxWidth(Double.MAX_VALUE);

        Label loginLabel = new Label();
        loginLabel.setText("Already have an account? Sign in.");
        loginLabel.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");

        button.setOnAction(e -> {
            String name = fullNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String repeatPassword = repeatPasswordField.getText();

            if (name.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                fullNameField.setStyle("-fx-border-color: red;");
                emailField.setStyle("-fx-border-color: red;");
                passwordField.setStyle("-fx-border-color: red;");
                Utils.sendMessage("Ops! Please complete all fields!", Alert.AlertType.WARNING);
                return;
            }

            String typeEmail = Utils.recognizeTypeOfEmail(email);
            if(typeEmail.equals("invalid")) {
                emailField.setStyle("-fx-border-color: red;");
                Utils.sendMessage("Type of Email wrong. Try with other.", Alert.AlertType.ERROR);
                return;
            }

            if(typeEmail.equals("unknown")) {
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

            if (userService.existsByEmail(email)) {
                Utils.sendMessage("Email already in use. Try another one.", Alert.AlertType.INFORMATION);
                return;
            }

            cleanFields(fullNameField, emailField, passwordField, repeatPasswordField);

            Roles defaultRole = roleService.findByName("USERS");
            if (defaultRole == null) {
                Utils.sendMessage("Role USER not found. Contact administrator.", Alert.AlertType.ERROR);
                return;
            }

            User user = new User(name, email, password);
            userService.saveUser(user);

            userRoleService.assignRoleToUser(user, defaultRole);

            Utils.sendMessage("¡Successfuly Register! Welcome, " + user.getFullName() + "! Now you can log in with your account.", Alert.AlertType.INFORMATION);
            Utils.loadWindowsToShow(new LoginPage(), primaryStage);
        });

        Utils.removeErrorStyleOnTyping(fullNameField);
        Utils.removeErrorStyleOnTyping(emailField);
        Utils.removeErrorStyleOnTyping(passwordField);
        Utils.removeErrorStyleOnTyping(repeatPasswordField);

        loginLabel.setOnMouseClicked(e -> {
            Utils.loadWindowsToShow(new LoginPage(), primaryStage);
        });

        VBox form = new VBox(10, titleBox, fullName, fullNameField, nameLabel, emailField, passwordLabel, passwordField, repeatPasswordLabel, repeatPasswordField, button, loginLabel);
        form.setPadding(new Insets(20));
        form.setMaxWidth(360);
        form.setFillWidth(true);

        return new StackPane(form);
    }

    private static void cleanFields(TextField fullNameField, TextField nameField, PasswordField passwordField, PasswordField repeatPasswordField) {
        fullNameField.clear();
        nameField.clear();
        passwordField.clear();
        repeatPasswordField.clear();
    }
}
