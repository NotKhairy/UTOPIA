package game.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
import javafx.stage.Stage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;

public class main extends Application {
	 
	 @Override
	 public void start(Stage primaryStage) {
		 String imagePath = "/HomeViewLooped.gif";
		 String iconPath = "/AttackOnTitan.jpg";

		 Image iconImage = new Image(getClass().getResourceAsStream(iconPath));  
	     Image backgroundImage = new Image(getClass().getResourceAsStream(imagePath));
	     ImageView backgroundImageView = new ImageView(backgroundImage);
	     
	     backgroundImageView.setPreserveRatio(false);
	     String instructionsText = "Instructions:\n" +
                 "1. Move your character using the arrow keys.\n" +
                 "2. Use the spacebar to attack the titans.\n" +
                 "3. Avoid colliding with titans or obstacles.\n" +
                 "4. Survive as long as possible to win!";
	     Label instructionsLabel = new Label(instructionsText);
	     
	     instructionsLabel.getStyleClass().add("instructions-label");
	     StackPane root = new StackPane();
	     HBox buttons = new HBox();
	     AnchorPane anchorPane = new AnchorPane();
	     ScrollPane scroll = new ScrollPane();
	     scroll.fitToWidthProperty();
	     scroll.fitToHeightProperty();
	     scroll.setContent(instructionsLabel);
	     
	     
	     Button easyModeButton = new Button("EASY");
	     easyModeButton.getStyleClass().add("custom-button");
	     easyModeButton.setPrefWidth(550);
	     
	     Button hardModeButton = new Button("HARD");
	     hardModeButton.getStyleClass().add("custom-button");
	     hardModeButton.setPrefWidth(550);
	     
	     buttons.getChildren().addAll(hardModeButton,easyModeButton);
	     buttons.setAlignment(Pos.CENTER);
	     buttons.setSpacing(20);
	     AnchorPane.setTopAnchor(scroll, 0.0);
	     AnchorPane.setBottomAnchor(buttons, 10.0);
	     AnchorPane.setLeftAnchor(buttons, 0.0);
	     AnchorPane.setRightAnchor(buttons, 0.0);
	     anchorPane.getChildren().addAll(buttons, scroll);
	     
	     root.getChildren().addAll(backgroundImageView,anchorPane);
	     Scene mainScene = new Scene(root, 1250, 690);
	     mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	     backgroundImageView.fitWidthProperty().bind(mainScene.widthProperty());
	     backgroundImageView.fitHeightProperty().bind(mainScene.heightProperty());
	     
	     
	     
	     primaryStage.setTitle("Utopia");
	     primaryStage.getIcons().add(iconImage);
	     primaryStage.setScene(mainScene);
	     primaryStage.show();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
}
