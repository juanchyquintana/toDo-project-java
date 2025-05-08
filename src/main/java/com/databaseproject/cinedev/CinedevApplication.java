package com.databaseproject.cinedev;

import com.databaseproject.cinedev.presentation.PathViews;
import com.databaseproject.cinedev.presentation.ViewSwitcher;
import com.databaseproject.cinedev.stages.LoginPage;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CinedevApplication extends Application {
	@Getter
    private static ConfigurableApplicationContext springContext;

	public static void main(String[] args) {
		Application.launch(CinedevApplication.class, args);
	}

	@Override
	public void init() {
		Dotenv dotenv = Dotenv.configure()
				.directory("./")
				.ignoreIfMalformed()
				.ignoreIfMissing()
				.load();

		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USER", dotenv.get("DB_USER"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		springContext = new SpringApplicationBuilder(CinedevApplication.class).run();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewSwitcher.switchTo(primaryStage, PathViews.LOGIN.getPathViews(), "Login", 600, 600);
	}

	@Override
	public void stop() {
		springContext.close();
	}

}
