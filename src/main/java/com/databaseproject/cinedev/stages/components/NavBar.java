package com.databaseproject.cinedev.stages.components;

import com.databaseproject.cinedev.CinedevApplication;
import com.databaseproject.cinedev.domain.models.base.User;
import com.databaseproject.cinedev.application.services.task.ITaskService;
import com.databaseproject.cinedev.stages.TaskPage;
import com.databaseproject.cinedev.stages.UserPage;
import com.databaseproject.cinedev.application.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;


public class NavBar extends HBox {
    ITaskService taskService;

    public NavBar(User user, Stage primaryStage) {
        this.taskService = CinedevApplication.getSpringContext().getBean(ITaskService.class);

        setPadding(new Insets(0, 15, 0, 15));
        setSpacing(20);
        setAlignment(Pos.CENTER_LEFT);
        setStyle("-fx-background-color: transparent;");

        StackPane logo = Utils.logoCine(100);

        HBox space = new HBox();
        space.setPrefWidth(Region.USE_COMPUTED_SIZE);
        HBox.setHgrow(space, Priority.ALWAYS);

        Label welcomeLabel = new Label(user.getFullName().toUpperCase());
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");


        Button checkoutButton = new Button("", Utils.typeOfIcon("fas-shopping-cart", "white"));
        checkoutButton.setStyle("-fx-background-color: #2ecc71;");
        checkoutButton.setMaxWidth(Double.MAX_VALUE);
        checkoutButton.setOnAction(e -> {

        });

        Button showUsers = new Button("Users".toUpperCase());
        showUsers.setStyle("-fx-background-color: #42A5F5; -fx-text-fill: white; -fx-font-weight: bold;");
        showUsers.setMaxWidth(Double.MAX_VALUE);
        showUsers.setOnAction(e -> {
            Utils.loadWindowsToShow(new UserPage(user), primaryStage);
            Utils.sendMessage("User's tasks loaded successfully!", Alert.AlertType.INFORMATION);
        });

        Button showTableTask = new Button("Tasks".toUpperCase());
        showTableTask.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        showTableTask.setMaxWidth(Double.MAX_VALUE);
        showTableTask.setOnAction(e -> {
            Utils.loadWindowsToShow(new TaskPage(user, taskService), primaryStage);
            Utils.sendMessage(user.getFullName() + "'s tasks loaded successfully!", Alert.AlertType.INFORMATION);
        });

        Button logOut = new Button("Log Out".toUpperCase());
        logOut.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");
        logOut.setMaxWidth(Double.MAX_VALUE);
        logOut.setOnAction(e -> {
            Utils.sendMessage("Good Bye, " + user.getFullName() + "! Thanks you for use Cinedev. See you soon!", Alert.AlertType.INFORMATION);
        });

        this.getChildren().addAll(logo, space, welcomeLabel, checkoutButton);

        // boolean isAdmin = userRoleService.isAdmin(user.getId());
//        if (isAdmin) {
//            this.getChildren().add(showUsers);
//        }

        this.getChildren().addAll(showTableTask, logOut);
    }
}
