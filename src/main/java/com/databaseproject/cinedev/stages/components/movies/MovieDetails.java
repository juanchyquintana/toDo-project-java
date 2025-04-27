package com.databaseproject.cinedev.stages.components.movies;

import com.databaseproject.cinedev.models.movie.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MovieDetails {

    public void showFormModal(Stage primaryStage, Movie movie) {
        BorderPane detailView = new BorderPane();
        detailView.setPadding(new Insets(20));

        ImageView imageView = new ImageView(new Image(MovieDetails.class.getResource("/images/movies/" + movie.getImageName()).toExternalForm()));
        imageView.setFitWidth(300);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(false);

        StackPane imagePane = new StackPane(imageView);
        imagePane.setMinHeight(450);
        imagePane.setMaxHeight(450);
        imagePane.setPrefHeight(450);
        imagePane.setPadding(Insets.EMPTY);
        VBox.setVgrow(imagePane, Priority.NEVER);

        Label titleLabel = new Label(movie.getTitle().toUpperCase());
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Label descLabel = new Label("Descripción: " + (movie.getDescription() != null ? movie.getDescription() : "No disponible"));
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-font-size: 15px;");
        descLabel.setMaxWidth(400);

        Button buyButton = new Button("Buy a Ticket");
        buyButton.setMaxWidth(Double.MAX_VALUE);
        buyButton.setStyle("""
                -fx-background-color: #4CAF50;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
        """);
        buyButton.setOnAction(e -> {
            SeatSelectionView seatSelectionView = new SeatSelectionView(movie);

            Stage seatStage = new Stage();
            seatStage.setTitle("Seat Selection - " + movie.getTitle());
            seatStage.setScene(seatSelectionView.showWindow(seatStage));
            seatStage.initModality(Modality.APPLICATION_MODAL);
            seatStage.initOwner(((Stage) ((Button) e.getSource()).getScene().getWindow()));
            seatStage.showAndWait();
        });

        VBox infoBox = new VBox(20, titleLabel, descLabel, buyButton);
        infoBox.setPadding(new Insets(20, 20, 20, 10));
        infoBox.setAlignment(Pos.TOP_LEFT);
        infoBox.setPrefWidth(450);

        HBox layout = new HBox(imagePane, infoBox);
        layout.setPadding(Insets.EMPTY);
        layout.setAlignment(Pos.TOP_LEFT);

        detailView.setCenter(layout);

        Scene scene = new Scene(detailView, 800, 500);
        Stage detailStage = new Stage();
        detailStage.setScene(scene);
        detailStage.setTitle("CineDev - " + movie.getTitle());
        detailStage.initOwner(primaryStage);
        detailStage.initModality(Modality.APPLICATION_MODAL);

        detailStage.sizeToScene();
        detailStage.showAndWait();
    }
}
