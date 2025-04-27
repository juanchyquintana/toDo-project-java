package com.databaseproject.cinedev.stages.components.forms;

import com.databaseproject.cinedev.CinedevApplication;
import com.databaseproject.cinedev.models.base.Roles;
import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.models.base.UserRoles;
import com.databaseproject.cinedev.models.base.compositeKey.UserRoleId;
import com.databaseproject.cinedev.services.base.roles.RoleService;
import com.databaseproject.cinedev.services.base.user.UserService;
import com.databaseproject.cinedev.utils.Utils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class UserEditForm {
    private UserService userService;
    private RoleService roleService;

    private User user;
    private Runnable userAdded;

    public UserEditForm(User user, Runnable userAdded) {
        this.userService = CinedevApplication.getSpringContext().getBean(UserService.class);
        this.roleService = CinedevApplication.getSpringContext().getBean(RoleService.class);
        this.user = user;
        this.userAdded = userAdded;
    }

    public void showFormModal(Stage primaryStage) {
        Stage modal = new Stage();
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));

        TextField nameField = new TextField(user.getFullName());
        nameField.setPromptText("Full name");

        TextField emailField = new TextField(user.getEmail());
        emailField.setPromptText("Email");

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

        List<Roles> allRoles = roleService.getAllRoles();
        ComboBox<Roles> roleSelector = new ComboBox<>(FXCollections.observableArrayList(allRoles));
        roleSelector.setMaxWidth(Double.MAX_VALUE);
        roleSelector.setPromptText("Select Role");

        roleSelector.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Roles item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getName());
            }
        });

        roleSelector.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Roles item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getName());
            }
        });

        if (user.getUserRoles() != null && !user.getUserRoles().isEmpty()) {
            Roles firstRole = user.getUserRoles().iterator().next().getRoles();
            roleSelector.setValue(firstRole);
        }

        Button saveButton = new Button("Save User");
        saveButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            Roles selectedRole = roleSelector.getValue();
            Roles managedRole = roleService.findById(selectedRole.getId());

            if (name.isEmpty() || email.isEmpty() || managedRole == null) {
                Utils.sendMessage("All fields are required, including role.", Alert.AlertType.WARNING);
                return;
            }

            boolean isNewUser = user.getId() == null;

            user.setFullName(name);
            user.setEmail(email);

            if (isNewUser) {
                if (password.isEmpty()) {
                    Utils.sendMessage("Password is required for new users.", Alert.AlertType.WARNING);
                    return;
                }
                user.setPassword(user.hashPassword(password));
                user.setCreatedAt(LocalDateTime.now());
            }

            User savedUser = userService.saveUser(user);

            UserRoles newUserRole = new UserRoles();
            newUserRole.setUser(savedUser);
            newUserRole.setRoles(managedRole);
            newUserRole.setId(new UserRoleId(savedUser.getId(), managedRole.getId()));

            savedUser.setUserRoles(Set.of(newUserRole));

            userService.saveUser(savedUser);

            userAdded.run();
            modal.close();
            Utils.sendMessage("User saved successfully!", Alert.AlertType.INFORMATION);
        });

        if (user.getId() == null) {
            form.getChildren().addAll(
                    new Label("Full Name"), nameField,
                    new Label("Email"), emailField,
                    new Label("Password"), passwordField,
                    new Label("Role"), roleSelector,
                    saveButton
            );
        } else {
            form.getChildren().addAll(
                    new Label("Full Name"), nameField,
                    new Label("Email"), emailField,
                    new Label("Role"), roleSelector,
                    saveButton
            );
        }

        Scene scene = new Scene(form, 400, 300);
        modal.setScene(scene);
        modal.initOwner(primaryStage);
        modal.setTitle(user.getId() == null ? "Add New User" : "Edit User");
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.showAndWait();
    }

}
