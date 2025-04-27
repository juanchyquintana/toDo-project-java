package com.databaseproject.cinedev.stages.components.cart;

import com.databaseproject.cinedev.models.movie.Ticket;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;

public class CheckoutController {
    private final List<Ticket> tickets;
    private final Label totalLabel;
    private final ComboBox<String> paymentMethod;
    private final Button confirmButton;

    public CheckoutController(List<Ticket> tickets, Label totalLabel, ComboBox<String> paymentMethod, Button confirmButton) {
        this.tickets = tickets;
        this.totalLabel = totalLabel;
        this.paymentMethod = paymentMethod;
        this.confirmButton = confirmButton;
    }

    public void updateTotal() {
        double total = tickets.stream().mapToDouble(Ticket::getPrice).sum();
        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    public void setupConfirmation() {
        confirmButton.setOnAction(e -> {
            String method = paymentMethod.getValue();
            if (method == null || method.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a payment method.");
                alert.show();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Purchase Confirmed");
            alert.setHeaderText("Thank you!");
            alert.setContentText("Your tickets have been booked.\nPayment Method: " + method);
            alert.showAndWait();
        });
    }
}
