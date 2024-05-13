package game.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class main extends Application {
	 
	 @Override
	 public void start(Stage stage) {
	  try {
	   Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
	   Scene scene = new Scene(root);
	   scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	   String css = this.getClass().getResource("style.css").toExternalForm();
	   scene.getStylesheets().add(css);
	   stage.setScene(scene);
	   stage.show();
	  } catch(Exception e) {
	   e.printStackTrace();
	  }
	 } 

	 public static void main(String[] args) {
	  launch(args);
	 }
	}
