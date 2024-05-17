package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.Battle;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.WeaponRegistry;
import game.engine.weapons.factory.WeaponFactory;
import javafx.scene.paint.Color;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
		String antiTitanShell = "/antiTitanShell.png";
		Image backgroundImage = new Image(getClass().getResourceAsStream(battleSceneBgPath));
		Image towerImage = new Image(getClass().getResourceAsStream(towerImagePath));
		Image antiTitanShellImage = new Image(getClass().getResourceAsStream(antiTitanShell));
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

		HashMap<Integer, WeaponRegistry> weaponShop = battle.getWeaponFactory().getWeaponShop();
		Label scoreLabel = new Label("Current score: "+ battle.getScore());
		Label currentTurnLabel = new Label("Current Turn: "+ battle.getNumberOfTurns());
		Label currentResourcesLabel = new Label("Current Resources: " + battle.getResourcesGathered());
		Button purchase = new Button("Purchase Weapon");
		Button passTurn = new Button("Pass"); passTurn.setLayoutY(150);
		Label availableLanesLabel = new Label("Available Lanes: " + battle.getLanes().size());
		Button shopExit = new Button ("exit");
		
		
		//Towers container
		ArrayList<VBox> towers = new ArrayList<VBox>();
		VBox towerStatus = new VBox();
		towers.add(towerStatus);
		towerStatus = new VBox();
		towers.add(towerStatus);
		towerStatus = new VBox();
		towers.add(towerStatus);
		if(Difficulty == "Hard") {
			towerStatus = new VBox();
			towers.add(towerStatus);
			towerStatus = new VBox();
			towers.add(towerStatus);
		}
		
		//adding towers to the grid
		int row = 0;
		int counter = 0;
		VBox Pointer = null;
		HashMap<VBox,Lane> imageToLaneRelations = new HashMap<VBox,Lane>();
		ArrayList<Lane> tmp = new ArrayList<Lane>();
		while(counter < towers.size()) {
			Pointer = towers.get(counter);
			
			Grid.add(Pointer, 0, row);
			tmp.add(counter, battle.getLanes().poll());
			imageToLaneRelations.put(Pointer,tmp.get(counter));
			row++;
			counter++;
		}
		battle.getLanes().addAll(tmp);
		
		for(int i=0;i<towers.size();i++) {
			towers.get(i).getChildren().addAll(new Label("Danger Level: "+ imageToLaneRelations.get(towers.get(i)).getDangerLevel()),new Label("Health: " + imageToLaneRelations.get(towers.get(i)).getLaneWall().getCurrentHealth()),new ImageView(towerImage));
			((ImageView) towers.get(i).getChildren().get(2)).setPreserveRatio(false);
			if(towers.size()==3) {
				((ImageView) towers.get(i).getChildren().get(2)).setFitHeight(190);
				((ImageView) towers.get(i).getChildren().get(2)).setFitWidth(190);
			} else {
				((ImageView) towers.get(i).getChildren().get(2)).setFitHeight(100);
				((ImageView) towers.get(i).getChildren().get(2)).setFitWidth(100);
			}
			
			((Label) towers.get(i).getChildren().get(0)).textProperty().bind(Bindings.concat("Danger Level: ", new SimpleIntegerProperty(imageToLaneRelations.get(towers.get(i)).getDangerLevel()).asString()));
		}

		
		
		
		
		VBox labelsContainer = new VBox();
		labelsContainer.getChildren().addAll(scoreLabel,currentTurnLabel,currentResourcesLabel,availableLanesLabel);
		labelsContainer.setBackground(blackbg);
		shopView.getChildren().addAll(shopExit,shop);
		shopView.setVisible(false);
		purchase.setLayoutY(100);
		shop.setLayoutX(600);
		shop.setLayoutY(345);
		shopExit.setLayoutX(1000);
		shopExit.setLayoutY(20);
		Grid.setHgap(10);
		Grid.setVgap(5);
//		Grid.setLayoutY(50);
//		Grid.setGridLinesVisible(true);
		root.getChildren().addAll(backgroundImageView,Grid, purchase, labelsContainer,shopView,passTurn);
		AnchorPane.setRightAnchor(labelsContainer, 0.0);
		AnchorPane.setRightAnchor(purchase, 0.0);
		AnchorPane.setRightAnchor(passTurn, 0.0);
		Scene scene = new Scene(root,1250,690);
		
		//shop container
		int shopRowIndex = 1;
		Label weaponName = new Label ("Name");
		Label weaponType = new Label("Type");
		Label priceLabel = new Label ("Price");
		Label damageLabel = new Label ("Damage");
		Spinner<Integer> numberSpinner = new Spinner<>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, battle.getLanes().size(), 0);
		numberSpinner.setValueFactory(valueFactory);

		shop.add(weaponName,0,0);
		shop.add(weaponType,1,0);
		shop.add(priceLabel,2,0);
		shop.add(damageLabel,3,0);
		shop.add(numberSpinner, 4,0);
		
		
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
						playerController.handleBuyButton(battle,1,imageToLaneRelations.get(towers.get(numberSpinner.getValue()-1)),root);
						((Label)labelsContainer.getChildren().get(0)).setText("Current score: "+ battle.getScore());
						((Label)labelsContainer.getChildren().get(1)).setText("Current Turn: "+ battle.getNumberOfTurns());
						((Label)labelsContainer.getChildren().get(2)).setText("Current Resources: " + battle.getResourcesGathered());
						((Label)labelsContainer.getChildren().get(3)).setText("Available Lanes: " + battle.getLanes().size());
						
					} catch (InsufficientResourcesException | InvalidLaneException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if(name.getText() == "Long Range Spear") {
					try {
						playerController.handleBuyButton(battle,2,imageToLaneRelations.get(towers.get(numberSpinner.getValue()-1)),root);
						((Label)labelsContainer.getChildren().get(0)).setText("Current score: "+ battle.getScore());
						((Label)labelsContainer.getChildren().get(1)).setText("Current Turn: "+ battle.getNumberOfTurns());
						((Label)labelsContainer.getChildren().get(2)).setText("Current Resources: " + battle.getResourcesGathered());
						((Label)labelsContainer.getChildren().get(3)).setText("Available Lanes: " + battle.getLanes().size());
					} catch (InsufficientResourcesException | InvalidLaneException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if(name.getText() == "Wall Spread Cannon") {
					try {
						playerController.handleBuyButton(battle,3,imageToLaneRelations.get(towers.get(numberSpinner.getValue()-1)),root);
						((Label)labelsContainer.getChildren().get(0)).setText("Current score: "+ battle.getScore());
						((Label)labelsContainer.getChildren().get(1)).setText("Current Turn: "+ battle.getNumberOfTurns());
						((Label)labelsContainer.getChildren().get(2)).setText("Current Resources: " + battle.getResourcesGathered());
						((Label)labelsContainer.getChildren().get(3)).setText("Available Lanes: " + battle.getLanes().size());
					} catch (InsufficientResourcesException | InvalidLaneException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						playerController.handleBuyButton(battle,4,imageToLaneRelations.get(towers.get(numberSpinner.getValue()-1)),root);
						((Label)labelsContainer.getChildren().get(0)).setText("Current score: "+ battle.getScore());
						((Label)labelsContainer.getChildren().get(1)).setText("Current Turn: "+ battle.getNumberOfTurns());
						((Label)labelsContainer.getChildren().get(2)).setText("Current Resources: " + battle.getResourcesGathered());
						((Label)labelsContainer.getChildren().get(3)).setText("Available Lanes: " + battle.getLanes().size());
						
						VBox weapons = new VBox();
						for(int i = 0; i<imageToLaneRelations.get(towers.get(numberSpinner.getValue()-1)).getWeapons().size();i++) {
							if(imageToLaneRelations.get(towers.get(numberSpinner.getValue()-1)).getWeapons().get(i) instanceof PiercingCannon ) {
								weapons.getChildren().add(new ImageView(antiTitanShellImage));								
							}
							if(imageToLaneRelations.get(towers.get(numberSpinner.getValue()-1)).getWeapons().get(i) instanceof SniperCannon ) {
								weapons.getChildren().add(new ImageView(antiTitanShellImage));								
							}
							if(imageToLaneRelations.get(towers.get(numberSpinner.getValue()-1)).getWeapons().get(i) instanceof VolleySpreadCannon ) {
								weapons.getChildren().add(new ImageView(antiTitanShellImage));								
							}
							if(imageToLaneRelations.get(towers.get(numberSpinner.getValue()-1)).getWeapons().get(i) instanceof WallTrap ) {
								weapons.getChildren().add(new ImageView(antiTitanShellImage));								
							}
						}
						for(int i = 0; i< weapons.getChildren().size();i++) {
							((ImageView)weapons.getChildren().get(i)).setFitHeight(50);
							((ImageView)weapons.getChildren().get(i)).setFitWidth(50);
						}
						Grid.add(weapons, 1, numberSpinner.getValue()-1);
						
					} catch (InsufficientResourcesException | InvalidLaneException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					shopView.setVisible(false);
					passTurn.setVisible(true);
				}
			});
			shopRowIndex ++;
		}
		
		shopExit.setOnAction(e -> {
			shopView.setVisible(false);
			passTurn.setVisible(true);
		});
		
		passTurn.setOnAction(e -> {
			purchase.setVisible(false);
			playerController.handlePassTurnButton(battle,root);
			//scoreLabel,currentTurnLabel,currentResourcesLabel,availableLanesLabel
			((Label)labelsContainer.getChildren().get(0)).setText("Current score: "+ battle.getScore());
			((Label)labelsContainer.getChildren().get(1)).setText("Current Turn: "+ battle.getNumberOfTurns());
			((Label)labelsContainer.getChildren().get(2)).setText("Current Resources: " + battle.getResourcesGathered());
			((Label)labelsContainer.getChildren().get(3)).setText("Available Lanes: " + battle.getLanes().size());
			
		});
		purchase.setOnAction(e -> {
			passTurn.setVisible(false);
			shopView.setVisible(true);
		});
		return scene;
	}
}
