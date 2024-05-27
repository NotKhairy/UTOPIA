package game.gui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Pos;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class MainScene {

	public Scene createScene() {
		SceneController Controller = new SceneController();
		BackgroundFill black = new BackgroundFill(Color.BLACK, null ,null);
		Background blackbg = new Background(black);
		String imagePath = "/HomeViewLooped.gif";  
		String instructionsTitle = "Instructions:\n";
		String instructionsText = 
				"1. Easy mode:\n"
				+ "In easy mode, you are given 3 lanes and 750 resources, \n"
				+ "you are required to survive as long as possible.\n" + "\n" +
				"2. Hard Mode:\n"
				+ "In easy mode, you are given 5 lanes and 375 resources, \n"
				+ "try your best to survive as long as possible\n" + "\n" +
				"3. To buy a weapon, click on its image \n" + "\n" +
				"4. WeaponShop is displayed infront of the towers,\n"
				+ "to get the info of a weapon simply hover over the\n "
				+ "weapon's image. You lose whenever the titans \n"
				+ "destroy all of your towers";
		Image backgroundImage = new Image(getClass().getResourceAsStream(imagePath));
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		
		

		
		Label instructionsTitleLabel = new Label(instructionsTitle);
		Label instructionsLabel = new Label(instructionsText);
		instructionsTitleLabel.getStyleClass().add("instructionsTitle-label");

		instructionsLabel.getStyleClass().add("instructions-label");
		StackPane root = new StackPane();
		HBox buttons = new HBox();
		AnchorPane anchorPane = new AnchorPane();
		ScrollPane scroll = new ScrollPane();
		VBox paragraph = new VBox();
		paragraph.getChildren().addAll(instructionsTitleLabel,instructionsLabel);
		paragraph.setAlignment(Pos.CENTER);
		paragraph.getStyleClass().add("paragraph-Vbox");
		scroll.setContent(paragraph);
		scroll.setPrefWidth(358);
		scroll.setPrefHeight(200.0);
		scroll.getStyleClass().add("paragraph-Vbox");
		
		Button easyModeButton = new Button("EASY");
		easyModeButton.getStyleClass().add("custom-button");
		easyModeButton.setPrefWidth(550);

		Button hardModeButton = new Button("HARD");
		hardModeButton.getStyleClass().add("custom-button");
		hardModeButton.setPrefWidth(550);

		buttons.getChildren().addAll(hardModeButton,easyModeButton);
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(20);
		
		
		AnchorPane.setTopAnchor(scroll, 345.0-100);
		AnchorPane.setLeftAnchor(scroll, 625.0-179);
		AnchorPane.setBottomAnchor(buttons, 10.0);
		AnchorPane.setLeftAnchor(buttons, 0.0);
		AnchorPane.setRightAnchor(buttons, 0.0);
		anchorPane.getChildren().addAll(buttons, scroll);

		root.getChildren().addAll(backgroundImageView,anchorPane);
		Scene mainScene = new Scene(root, 1250, 690);
		mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		backgroundImageView.fitWidthProperty().bind(mainScene.widthProperty());
		backgroundImageView.fitHeightProperty().bind(mainScene.heightProperty());
		
		easyModeButton.setOnAction(e -> {
			try {
				Controller.switchToBattleScene(e,"Easy");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		hardModeButton.setOnAction(e -> {
			try {
				Controller.switchToBattleScene(e,"Hard");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		return mainScene;
	}

}
