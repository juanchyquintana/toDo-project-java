package com.databaseproject.cinedev.stages.components.cart;

import com.databaseproject.cinedev.models.movie.Ticket;
import com.databaseproject.cinedev.stages.IWindowScene;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class CheckoutView implements IWindowScene {
    private final List<Ticket> tickets;

    public CheckoutView(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public Scene showWindow(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("""
                -fx-background-color: #f0f8ff;
                -fx-border-color: #3498db;
                -fx-border-width: 2px;
                -fx-border-radius: 8px;
        """);

        Label titleLabel = new Label("Confirm Your Tickets");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        ListView<Ticket> ticketList = new ListView<>();
        ticketList.getItems().addAll(tickets);
        ticketList.setPrefHeight(180);
        ticketList.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc;");

        Label totalLabel = new Label();
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        totalLabel.setStyle("-fx-text-fill: #e74c3c;");

        ComboBox<String> paymentMethod = new ComboBox<>();
        paymentMethod.getItems().addAll("Credit Card", "Debit Card", "Cash", "Mobile Payment");
        paymentMethod.setPromptText("Select Payment Mehotd");
        paymentMethod.setStyle("-fx-background-color: #ecf0f1;");

        Button confirmButton = new Button("Confirm Purchase");
        confirmButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        confirmButton.setStyle(
                "-fx-background-color: #27ae60; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-radius: 8px"
        );

        layout.getChildren().addAll(titleLabel, ticketList, totalLabel, paymentMethod, confirmButton);

        CheckoutController controller = new CheckoutController(tickets, totalLabel, paymentMethod, confirmButton);
        controller.updateTotal();
        controller.setupConfirmation();

        return new Scene(layout, 500, 400);
    }
}
