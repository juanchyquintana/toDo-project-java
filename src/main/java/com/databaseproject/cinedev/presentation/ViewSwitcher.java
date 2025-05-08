package com.databaseproject.cinedev.presentation;

import com.databaseproject.cinedev.CinedevApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class ViewSwitcher {
    private static final ApplicationContext context = CinedevApplication.getSpringContext();

    public static void switchTo(Stage stage, String pathOfView, String title, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewSwitcher.class.getResource(pathOfView));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle("Cinedev To-Do - " + title);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error to loading view: " + pathOfView);
        }
    }
}
