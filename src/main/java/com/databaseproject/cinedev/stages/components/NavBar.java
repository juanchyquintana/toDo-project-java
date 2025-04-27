package com.databaseproject.cinedev.stages.components;

import com.databaseproject.cinedev.CinedevApplication;
import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.models.movie.Ticket;
import com.databaseproject.cinedev.services.base.userRole.UserRoleService;
import com.databaseproject.cinedev.services.tasks.task.ITaskService;
import com.databaseproject.cinedev.stages.LoginPage;
import com.databaseproject.cinedev.stages.TaskPage;
import com.databaseproject.cinedev.stages.UserPage;
import com.databaseproject.cinedev.stages.components.cart.CheckoutView;
import com.databaseproject.cinedev.utils.Utils;
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
    UserRoleService userRoleService;

    public NavBar(User user, Stage primaryStage) {
        this.taskService = CinedevApplication.getSpringContext().getBean(ITaskService.class);
        this.userRoleService = CinedevApplication.getSpringContext().getBean(UserRoleService.class);

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

        List<Ticket> userTickets = List.of(
                new Ticket(8.50, LocalDateTime.now(), user),
                new Ticket(8.50, LocalDateTime.now(), user)
        );

        Button checkoutButton = new Button("", Utils.typeOfIcon("fas-shopping-cart", "white"));
        checkoutButton.setStyle("-fx-background-color: #2ecc71;");
        checkoutButton.setMaxWidth(Double.MAX_VALUE);
        checkoutButton.setOnAction(e -> {
            CheckoutView checkoutView = new CheckoutView(userTickets);
            Scene scene = checkoutView.showWindow(primaryStage);

            Stage modal = new Stage();
            modal.setScene(scene);
            modal.setTitle("CineDev - Checkout");
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initOwner(primaryStage);
            modal.setResizable(false);
            modal.showAndWait();
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
            Utils.loadWindowsToShow(new LoginPage(), primaryStage);
            Utils.sendMessage("Good Bye, " + user.getFullName() + "! Thanks you for use Cinedev. See you soon!", Alert.AlertType.INFORMATION);
        });

        this.getChildren().addAll(logo, space, welcomeLabel, checkoutButton);

        boolean isAdmin = userRoleService.isAdmin(user.getId());
        if (isAdmin) {
            this.getChildren().add(showUsers);
        }

        this.getChildren().addAll(showTableTask, logOut);
    }
}
