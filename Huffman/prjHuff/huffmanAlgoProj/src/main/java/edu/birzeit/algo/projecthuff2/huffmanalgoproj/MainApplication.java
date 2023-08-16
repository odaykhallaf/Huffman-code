package edu.birzeit.algo.projecthuff2.huffmanalgoproj;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class MainApplication extends Application {


	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 663, 417);
		stage.setTitle("Hello!");
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setResizable(true);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}
}
