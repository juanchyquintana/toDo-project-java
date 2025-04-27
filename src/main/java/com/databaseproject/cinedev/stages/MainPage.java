package com.databaseproject.cinedev.stages;

import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.models.movie.Movie;
import com.databaseproject.cinedev.stages.components.NavBar;
import com.databaseproject.cinedev.stages.components.movies.MovieDetails;
import com.databaseproject.cinedev.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class MainPage implements IWindowScene {
    private User user;

    public MainPage(User existingUser) {
        this.user = existingUser;
    }

    @Override
    public Scene showWindow(Stage primaryStage) {
        StackPane mainPage = mainPage(primaryStage);
        NavBar navBar = new NavBar(this.user, primaryStage);

        VBox root = new VBox(navBar, mainPage);
        root.setBackground(Utils.backgroundImageWindows());
        root.setSpacing(10);

        return new Scene(root, 1280, 720);
    }

    private StackPane mainPage(Stage primaryStage) {
        Label welcome = new Label("Bienvenido " + user.getFullName() +"!");
        welcome.setFont(new Font(18));
        welcome.setTextFill(Color.WHITE);
        welcome.setAlignment(Pos.TOP_LEFT);

        VBox premiereBox = createSection("Estreno", List.of(
                new Movie("Pitufos", "pitufos.jpg"),
                new Movie("Rapido y Furioso", "rapido.jpg"),
                new Movie("Nemo", "nemo.jpg"),
                new Movie("Lucy", "lucy.jpg")
        ), primaryStage);

        VBox nextBox = createSection("Proximamente", List.of(
                new Movie("Coco", "coco.jpg"),
                new Movie("Walle", "walle.jpg"),
                new Movie("Flash", "flash.jpg"),
                new Movie("Falcon", "falcon.jpg")
        ), primaryStage);

        VBox layout = new VBox(20, welcome, premiereBox, nextBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 20, 10, 20));

        return new StackPane(layout);
    }

    private VBox createSection(String titleText, List<Movie> movies, Stage primaryStage) {
        Label title = new Label(titleText);
        title.setFont(Font.font(16));
        title.setTextFill(Color.ORANGE);

        HBox movieBox = new HBox(30);
        movieBox.setAlignment(Pos.CENTER);
        movies.forEach(movie -> {
            VBox card = createMovieCard(movie, primaryStage);
            HBox.setMargin(card, new Insets(0, 60, 0, 60));
            movieBox.getChildren().add(card);
        });

        VBox section = new VBox(10);
        section.getChildren().addAll(title, movieBox);

        return section;
    }

    private VBox createMovieCard(Movie movie, Stage primaryStage) {
        Image image  = new Image(MainPage.class.getResourceAsStream("/images/movies/" + movie.getImageName()));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        Label title = new Label(movie.getTitle());
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        VBox cardMovie = new VBox(imageView, title);
        cardMovie.setAlignment(Pos.CENTER);
        cardMovie.setSpacing(10);
        cardMovie.setStyle("-fx-background-color: #444; -fx-padding: 10; -fx-background-radius: 10;");

        cardMovie.setOnMouseClicked(e -> new MovieDetails().showFormModal(primaryStage, movie));

        return cardMovie;
    }
}
