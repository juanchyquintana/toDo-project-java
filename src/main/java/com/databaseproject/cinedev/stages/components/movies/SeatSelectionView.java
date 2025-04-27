package com.databaseproject.cinedev.stages.components.movies;

import com.databaseproject.cinedev.models.movie.Movie;
import com.databaseproject.cinedev.stages.IWindowScene;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class SeatSelectionView implements IWindowScene {
    private final Set<Button> selectedSeats = new HashSet<>();
    private final Movie movie;

    public SeatSelectionView(Movie movie) {
        this.movie = movie;
    }

    @Override
    public Scene showWindow(Stage primaryStage) {
        return seatSelection();
    }

    private Scene seatSelection() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #8B0000;");

        Image posterImage = new Image(MovieDetails.class.getResource("/images/movies/" + movie.getImageName()).toExternalForm());
        ImageView poster = new ImageView(posterImage);
        poster.setFitWidth(150);
        poster.setPreserveRatio(true);

        Label title = new Label(movie.getTitle());
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label cast = new Label("Cast: Scarlett Johansson, Morgan Freeman");
        cast.setStyle("-fx-text-fill: white;");

        Label director = new Label("Director: Luc Besson");
        director.setStyle("-fx-text-fill: white;");

        Label rating = new Label("Rating: PG-13");
        rating.setStyle("-fx-text-fill: white;");

        Label description = new Label(movie.getDescription());
        description.setWrapText(true);
        description.setMaxWidth(300);
        description.setStyle("-fx-text-fill: white;");

        Label legend = new Label("\u25A0 Green: Available    \u25A0 Red: Unavailable");
        legend.setStyle("-fx-text-fill: white; -fx-font-style: italic;");

        VBox movieDetails = new VBox(5, title, cast, director, rating, description, legend);

        VBox posterBox = new VBox(10, poster, movieDetails);

        Label screenLabel = new Label("SCREEN");
        screenLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        GridPane seatGrid = createSeatGrid();

        Button confirmButton = new Button("CONFIRM");
        confirmButton.setMaxWidth(Double.MAX_VALUE);
        confirmButton.setStyle("""
                -fx-background-color: #4CAF50;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
        """);
        confirmButton.setOnAction(e -> {
            if (!selectedSeats.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Ticket successfully purchased!");
                alert.showAndWait();
            }
        });

        VBox rightPanel = new VBox(10, screenLabel, seatGrid, confirmButton);
        HBox controls = new HBox(30, posterBox, rightPanel);
        root.getChildren().add(controls);

        return new Scene(root, 800, 500);
    }

    private GridPane createSeatGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                Button seat = new Button("\u25A0");
                seat.setPrefSize(30, 30);

                if ((row + col) % 7 == 0) {
                    seat.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                    seat.setDisable(true);
                } else {
                    seat.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    seat.setOnAction(e -> handleSeatSelection(seat));
                }

                grid.add(seat, col, row);
            }
        }

        return grid;
    }
    private void handleSeatSelection(Button seat) {
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
            seat.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        } else {
            selectedSeats.add(seat);
            seat.setStyle("-fx-background-color: gold; -fx-text-fill: black;");
        }
    }

}
