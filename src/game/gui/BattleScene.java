package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.weapons.WeaponRegistry;
import game.engine.weapons.factory.WeaponFactory;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BattleScene {
	public Scene createScene(String Difficulty) throws IOException {
		String battleSceneBgPath = "/battleSceneBg.png";
		String towerImagePath = "/tower.png";
		Image backgroundImage = new Image(getClass().getResourceAsStream(battleSceneBgPath));
		Image towerImage = new Image(getClass().getResourceAsStream(towerImagePath));
		CornerRadii radius10 = new CornerRadii(10);
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		BackgroundFill black = new BackgroundFill(Color.BLACK, radius10 ,null);
		Background blackbg = new Background(black);
		
		
		int initalNumberOfLanes = 3;
		int initalResourcesPerLane = 250;
		
		if(Difficulty == "Hard") {
			initalNumberOfLanes = 5; 
			initalResourcesPerLane = 125;
			}
		Battle battle = new Battle(1,0,1200,initalNumberOfLanes,initalResourcesPerLane);
		PlayerController playerController = new PlayerController();
		
		Pane shopView = new Pane(); shopView.setVisible(false);
		GridPane shop = new GridPane(); 
		GridPane Grid = new GridPane();
		AnchorPane root = new AnchorPane();
		
		String score = "Current score: "+ battle.getScore();
		String currentTurn = "Current Turn: "+ battle.getNumberOfTurns();
		String currentResources = "Current Resources: " + battle.getResourcesGathered();
		HashMap<Integer, WeaponRegistry> weaponShop = battle.getWeaponFactory().getWeaponShop();
		String availableLanes = "Available Lanes: " + battle.getLanes().size();
		
		Label scoreLabel = new Label(score);
		Label currentTurnLabel = new Label(currentTurn);
		Label currentResourcesLabel = new Label(currentResources);
		Button purchase = new Button("Purchase Weapon");
		Button passTurn = new Button("Pass"); passTurn.setLayoutY(150);
		Label availableLanesLabel = new Label(availableLanes);
		
		passTurn.setOnAction(e -> {
			purchase.setVisible(false);
			battle.passTurn();
		});
		
		purchase.setOnAction(e -> {
			passTurn.setVisible(false);
			shopView.setVisible(true);
		});

		//shop container
		
		Button exit = new Button ("exit");
		exit.setOnAction(e -> {
			shopView.setVisible(false);
			passTurn.setVisible(true);
		});

		int shopRowIndex = 1;
		Label weaponName = new Label ("Name");
		Label weaponType = new Label("Type");
		Label priceLabel = new Label ("Price");
		Label damageLabel = new Label ("Damage");
		shop.add(weaponName,0,0);
		shop.add(weaponType,1,0);
		shop.add(priceLabel,2,0);
		shop.add(damageLabel,3,0);
		while (shopRowIndex<=weaponShop.size()) {
			Label damage = new Label(String.valueOf(weaponShop.get(shopRowIndex).getDamage()));
			Label name = new Label(String.valueOf(weaponShop.get(shopRowIndex).getName()));
			Label type = new Label(String.valueOf(weaponShop.get(shopRowIndex).getType()));
			Label price = new Label(String.valueOf(weaponShop.get(shopRowIndex).getPrice()));
			shop.add(name,0,shopRowIndex);
			shop.add(type,1,shopRowIndex);
			shop.add(price,2,shopRowIndex);
			shop.add(damage,3,shopRowIndex);
			Button buy = new Button("Buy");
			shop.add(buy,4,shopRowIndex);
			buy.setOnAction(e -> {
				if (name.getText() == "Anti Titan Shell") {
					try {
						playerController.handleBuyButton(battle,1,battle.getLanes().peek());
					} catch (InsufficientResourcesException | InvalidLaneException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if(name.getText() == "Long Range Spear") {
					try {
						playerController.handleBuyButton(battle,2,battle.getLanes().peek());
					} catch (InsufficientResourcesException | InvalidLaneException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if(name.getText() == "Wall Spread Cannon") {
					try {
						playerController.handleBuyButton(battle,3,battle.getLanes().peek());
					} catch (InsufficientResourcesException | InvalidLaneException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						playerController.handleBuyButton(battle,4,battle.getLanes().peek());
					} catch (InsufficientResourcesException | InvalidLaneException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					shopView.setVisible(false);
					purchase.setVisible(false);
				}
			});
			shopRowIndex ++;
		}
		
		//Towers container
		ArrayList<VBox> towers = new ArrayList<VBox>();
		VBox b = new VBox();
		b.getChildren().addAll(new Label("Danger Level: "),new Label("Health: "), new ImageView(towerImage));
		towers.add(b);
		b = new VBox();
		b.getChildren().addAll(new Label("Danger Level: "),new Label("Health: "),new ImageView(towerImage));
		towers.add(b);
		b = new VBox();
		b.getChildren().addAll(new Label("Danger Level: "),new Label("Health: "),new ImageView(towerImage));
		towers.add(b);
		
		if(Difficulty == "Hard") {
			b = new VBox();
			b.getChildren().addAll(new Label("Danger Level: "),new Label("Health: "),new ImageView(towerImage));
			towers.add(b);
			b = new VBox();
			b.getChildren().addAll(new Label("Danger Level: "),new Label("Health: "),new ImageView(towerImage));
			towers.add(b);
		}
		
		int row = 0;
		int counter = 0;
		VBox Pointer = null;
		while(counter < towers.size()) {
			Pointer = towers.get(counter);
			((ImageView) Pointer.getChildren().get(2)).setPreserveRatio(false);
			if(towers.size()==3) {
				((ImageView) Pointer.getChildren().get(2)).setFitHeight(190);
				((ImageView) Pointer.getChildren().get(2)).setFitWidth(190);
			} else {
				((ImageView) Pointer.getChildren().get(2)).setFitHeight(100);
				((ImageView) Pointer.getChildren().get(2)).setFitWidth(100);
			}
			Grid.add(Pointer, 0, row);
			row++;
			counter++;
		}

		
		
		VBox labelsContainer = new VBox();
		labelsContainer.getChildren().addAll(scoreLabel,currentTurnLabel,currentResourcesLabel,availableLanesLabel);
		labelsContainer.setBackground(blackbg);
		shopView.getChildren().addAll(exit,shop);
		shopView.setVisible(false);
		purchase.setLayoutY(100);
		shop.setLayoutX(600);
		shop.setLayoutY(345);
		exit.setLayoutX(1000);
		exit.setLayoutY(20);
		Grid.setHgap(10);
		Grid.setVgap(5);
//		Grid.setLayoutY(50);
//		Grid.setGridLinesVisible(true);
		root.getChildren().addAll(backgroundImageView,Grid, purchase, labelsContainer,shopView,passTurn);
		AnchorPane.setRightAnchor(labelsContainer, 0.0);
		AnchorPane.setRightAnchor(purchase, 0.0);
		AnchorPane.setRightAnchor(passTurn, 0.0);
		Scene scene = new Scene(root,1250,690);
		return scene;
	}
}
