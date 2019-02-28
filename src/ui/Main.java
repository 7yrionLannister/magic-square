package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MagicSquareUI.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Generate your own Magic Square!");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}