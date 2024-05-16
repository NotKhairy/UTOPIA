package game.gui;

import java.io.IOException;

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
import javafx.scene.paint.Color;

public class MainScene {

	public Scene createScene() {
		SceneController Controller = new SceneController();
		BackgroundFill black = new BackgroundFill(Color.BLACK, null ,null);
		Background blackbg = new Background(black);
		String imagePath = "/HomeViewLooped.gif";  
		String instructionsTitle = "Instructions:\n";
		String instructionsText = 
				"1. Move your character using the arrow keys.\n" +
				"2. Use the spacebar to attack the titans.\n" +
				"3. Avoid colliding with titans or obstacles.\n" +
				"4. Survive as long as possible to win!";
		Image backgroundImage = new Image(getClass().getResourceAsStream(imagePath));
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		
		Label instructionsTitleLabel = new Label("INSTRUCTIONS");
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
		scroll.setPrefSize(200, 160);
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
		
		
		AnchorPane.setTopAnchor(scroll, 345.0-scroll.getWidth()/2);
		AnchorPane.setLeftAnchor(scroll, 600.0-scroll.getHeight()/2);
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
