package game.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;

public class main extends Application {

	@Override
	public void start(Stage primaryStage) {
		// MediaPlayer mediaPlayer =
		// MediaPlayerSingleton.getInstance("/mainMenuTrack.mp3");
		// mediaPlayer.play();
		MainScene mainScene = new MainScene();

		String iconPath = "/AttackOnTitan.jpg";
		Image iconImage = new Image(getClass().getResourceAsStream(iconPath));

		primaryStage.setTitle("Utopia");
		primaryStage.getIcons().add(iconImage);
		primaryStage.setResizable(false);
		primaryStage.setScene(mainScene.createScene());
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
