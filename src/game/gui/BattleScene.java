package game.gui;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import game.engine.Battle;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import javafx.scene.paint.Color;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class BattleScene {
	public Scene createScene(String Difficulty) throws IOException {
		SceneController Controller = new SceneController();
		String battleSceneBgPath = "/battleSceneBg.jpeg";
		String towerImagePath = "/tower.png";
		String antiTitanShell = "/antiTitanShell.png";
		String longRangeSpear = "/longRangeSpear.png";
		String wallSpreadCannon = "/wallSpreadCannon.png";
		String proximityTrap = "/proximityTrap.png";
		String pureTitan = "/pureTitan.png";
		String colossalTitan = "/colossalTitan.png";
		String armoredTitan = "/armoredTitan.png";
		String abnormalTitan = "/abnormalTitan.png";
		Image backgroundImage = new Image(getClass().getResourceAsStream(battleSceneBgPath));
		Image towerImage = new Image(getClass().getResourceAsStream(towerImagePath));
		CornerRadii radius10 = new CornerRadii(10);
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		BackgroundFill black = new BackgroundFill(Color.BLACK, radius10 ,null);
		Background blackbg = new Background(black);



		int initalNumberOfLanes = 3;
		int initalResourcesPerLane = 250;

		if(Difficulty.equals("Hard")) {
			initalNumberOfLanes = 5; 
			initalResourcesPerLane = 125;
		}
		final Battle battle = new Battle(1, 0, 900, initalNumberOfLanes, initalResourcesPerLane);
		PlayerController playerController = new PlayerController();
		ArrayList<Lane> battleLanes = new ArrayList<Lane>();
		ArrayList<VBox> titansAlive = new ArrayList<VBox>();
		battle.refillApproachingTitans();
		HashMap<ImageView,Titan> imageToTitanRelation = new HashMap<ImageView,Titan>();


		while(!battle.getLanes().isEmpty()) {
			battleLanes.add(battle.getLanes().poll());
		}
		for(int i = 0; i < battleLanes.size(); i++) {
			battle.getLanes().add(battleLanes.get(i));
		}

		GridPane Grid = new GridPane();
		AnchorPane root = new AnchorPane();
		Alert insufficientResources = new Alert(Alert.AlertType.INFORMATION);
		insufficientResources.setTitle("Error!");
		insufficientResources.setHeaderText("Insufficient Rresources");
		insufficientResources.setContentText("You don't have enough resources to purchase this weapon.\n"
				+ "Try another weapon or kill more titans to gain enough resources.");
		Alert invalidLane = new Alert(Alert.AlertType.INFORMATION);
		invalidLane.setTitle("Error!");
		invalidLane.setHeaderText("Invalid Lane");
		invalidLane.setContentText("You can't add a weapon to a lost lane.\n"
				+ "Try another lane.");

		HashMap<Integer, WeaponRegistry> weaponShop = battle.getWeaponFactory().getWeaponShop();
		Label scoreLabel = new Label("Current score: " + battle.getScore());
		scoreLabel.setStyle("-fx-text-fill: white");
		Label currentTurnLabel = new Label("Current Turn: " + battle.getNumberOfTurns());
		currentTurnLabel.setStyle("-fx-text-fill: white");
		Label currentResourcesLabel = new Label("Current Resources: " + battle.getResourcesGathered());
		currentResourcesLabel.setStyle("-fx-text-fill: white");
		Button passTurn = new Button("Pass"); passTurn.setLayoutY(150);
		passTurn.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF; -fx-font-size: 24px; -fx-font-family: 'Franklin Gothic Heavy'; -fx-pref-height: 40px;");
		Label availableLanesLabel = new Label("Available Lanes: " + battle.getLanes().size());
		availableLanesLabel.setStyle("-fx-text-fill: white");
		Label currentPhase = new Label("Current Phase: " + battle.getBattlePhase().toString());
		currentPhase.setStyle("-fx-text-fill: white");
		Button simulateGame = new Button("Simulate");
		simulateGame.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF; -fx-font-size: 24px; -fx-font-family: 'Franklin Gothic Heavy'; -fx-pref-height: 40px;");

		// Setting the column dims
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setPrefWidth(150);

		RowConstraints row0 = new RowConstraints();
		row0.setPrefHeight(110);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPrefWidth(40);

		RowConstraints row1 = new RowConstraints();
		row1.setPrefHeight(110);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHgrow(Priority.ALWAYS);

		RowConstraints row2 = new RowConstraints();
		row2.setPrefHeight(110);
		RowConstraints row3 = new RowConstraints();
		row3.setPrefHeight(110);
		RowConstraints row4 = new RowConstraints();
		row4.setPrefHeight(110);

		Grid.getColumnConstraints().addAll(column0, column1, column2);
		Grid.getRowConstraints().addAll(row0,row1,row2,row3,row4);
		Grid.setGridLinesVisible(true);

		// Towers container
		ArrayList<VBox> towers = new ArrayList<VBox>();
		VBox towerStatus = new VBox();
		towers.add(towerStatus);
		towerStatus = new VBox();
		towers.add(towerStatus);
		towerStatus = new VBox();
		towers.add(towerStatus);
		if(Difficulty.equals("Hard")) {
			towerStatus = new VBox();
			towers.add(towerStatus);
			towerStatus = new VBox();
			towers.add(towerStatus);
		}

		// Adding towers to the grid
		int row = 0;
		int counter = 0;
		VBox Pointer;
		HashMap<VBox, Lane> imageToLaneRelations = new HashMap<VBox, Lane>();
		while(counter < towers.size()) {
			Pointer = towers.get(counter);

			Grid.add(Pointer, 0, row);
			imageToLaneRelations.put(Pointer, battleLanes.get(counter));
			row++;
			counter++;
		}

		for(int i = 0; i < towers.size(); i++) {
			towers.get(i).getChildren().addAll(new Label("Danger Level: " + imageToLaneRelations.get(towers.get(i)).getDangerLevel()), new Label("Health: " + imageToLaneRelations.get(towers.get(i)).getLaneWall().getCurrentHealth()), new ImageView(towerImage));
			((ImageView) towers.get(i).getChildren().get(2)).setPreserveRatio(true);
			((ImageView) towers.get(i).getChildren().get(2)).setFitHeight(70);
			((ImageView) towers.get(i).getChildren().get(2)).setFitWidth(170);

			((Label) towers.get(i).getChildren().get(0)).textProperty().bind(Bindings.concat("Danger Level: ", imageToLaneRelations.get(towers.get(i)).getDangerLevelIntegerProperty()));
			((Label) towers.get(i).getChildren().get(0)).setStyle("-fx-text-fill: white");
			((Label) towers.get(i).getChildren().get(1)).textProperty().bind(Bindings.concat("Current Health: ", imageToLaneRelations.get(towers.get(i)).getLaneWall().getCurrentHealthIntegerProperty()));
			((Label) towers.get(i).getChildren().get(1)).setStyle("-fx-text-fill: white");
		}


		VBox labelsContainer = new VBox();
		labelsContainer.getChildren().addAll(scoreLabel, currentTurnLabel, currentResourcesLabel, availableLanesLabel, currentPhase);
		labelsContainer.setBackground(blackbg);
		Grid.setHgap(10);
		Grid.setVgap(5);
		root.getChildren().addAll(backgroundImageView, Grid, labelsContainer, passTurn, simulateGame);
		AnchorPane.setTopAnchor(Grid, 90.0);
		AnchorPane.setRightAnchor(labelsContainer, 0.0);
		AnchorPane.setTopAnchor(passTurn, 0.0);
		AnchorPane.setLeftAnchor(passTurn, 500.0);
		AnchorPane.setTopAnchor(simulateGame, 0.0);
		AnchorPane.setLeftAnchor(simulateGame, 750.0);
		Scene scene = new Scene(root, 1250, 690);


		// Creating the weapon images


		for (int i = 0; i < towers.size(); i++) {
			final int index = i; // Make a final copy of i for use in the lambda expression
			VBox weapons = new VBox();

			HBox antiTitanContainer = new HBox();
			Label weaponCounter = new Label("0");
			weaponCounter.setStyle("-fx-text-fill: white");
			ImageView antiTitanImage = new ImageView(new Image(getClass().getResourceAsStream(antiTitanShell)));
			antiTitanImage.setFitHeight(25);
			antiTitanImage.setFitWidth(25);
			antiTitanContainer.getChildren().addAll(antiTitanImage, weaponCounter);
			Tooltip toolTip1 = new Tooltip(
					"Name: " + weaponShop.get(1).getName() + "\n"
							+ "Type: " + weaponShop.get(1).getType() + "\n"
							+ "Price: "+ weaponShop.get(1).getPrice() + "\n"
							+ "Damage Points: "+ weaponShop.get(1).getDamage()+"\n"
							+ "Click on image to purchase");
			Tooltip.install(antiTitanContainer, toolTip1);

			HBox LongRangeSpearContainer = new HBox();
			Label weaponCounter2 = new Label("0");
			weaponCounter2.setStyle("-fx-text-fill: white");
			ImageView longSpearImage = new ImageView(new Image(getClass().getResourceAsStream(longRangeSpear)));
			longSpearImage.setFitHeight(25);
			longSpearImage.setFitWidth(25);
			LongRangeSpearContainer.getChildren().addAll(longSpearImage, weaponCounter2);
			Tooltip toolTip2 = new Tooltip(
					"Name: " + weaponShop.get(2).getName() + "\n"
							+ "Type: " + weaponShop.get(2).getType() + "\n"
							+ "Price: "+ weaponShop.get(2).getPrice() + "\n"
							+ "Damage Points: "+ weaponShop.get(2).getDamage()+"\n"
							+ "Click on image to purchase");
			Tooltip.install(LongRangeSpearContainer, toolTip2);

			HBox WallSpreadCannonContainer = new HBox();
			Label weaponCounter3 = new Label("0");
			weaponCounter3.setStyle("-fx-text-fill: white");
			ImageView wallSpreadCannonImage = new ImageView(new Image(getClass().getResourceAsStream(wallSpreadCannon)));
			wallSpreadCannonImage.setFitHeight(25);
			wallSpreadCannonImage.setFitWidth(25);
			WallSpreadCannonContainer.getChildren().addAll(wallSpreadCannonImage, weaponCounter3);
			Tooltip toolTip3 = new Tooltip(
					"Name: " + weaponShop.get(3).getName() + "\n"
							+ "Type: " + weaponShop.get(3).getType() + "\n"
							+ "Price: "+ weaponShop.get(3).getPrice() + "\n"
							+ "Damage Points: "+ weaponShop.get(3).getDamage()+"\n"
							+ "Click on image to purchase");
			Tooltip.install(WallSpreadCannonContainer, toolTip3);


			HBox ProximityTrapContainer = new HBox();
			Label weaponCounter4 = new Label("0");
			weaponCounter4.setStyle("-fx-text-fill: white");
			ImageView proximityTrapImage = new ImageView(new Image(getClass().getResourceAsStream(proximityTrap)));
			proximityTrapImage.setFitHeight(25);
			proximityTrapImage.setFitWidth(25);
			ProximityTrapContainer.getChildren().addAll(proximityTrapImage, weaponCounter4);
			Tooltip toolTip4 = new Tooltip(
					"Name: " + weaponShop.get(4).getName() + "\n"
							+ "Type: " + weaponShop.get(4).getType() + "\n"
							+ "Price: "+ weaponShop.get(4).getPrice() + "\n"
							+ "Damage Points: "+ weaponShop.get(4).getDamage()+"\n"
							+ "Click on image to purchase");
			Tooltip.install(ProximityTrapContainer, toolTip4);

			weapons.getChildren().addAll(antiTitanContainer, LongRangeSpearContainer, WallSpreadCannonContainer, ProximityTrapContainer);
			Grid.add(weapons, 1, i);

			antiTitanImage.setOnMouseClicked(event -> handleWeaponPurchase(playerController, battle, imageToLaneRelations.get(towers.get(index)), 1, insufficientResources, invalidLane, labelsContainer, Grid, index, 0, PiercingCannon.class, titansAlive, imageToTitanRelation, battleLanes, imageToLaneRelations, root, Controller));
			longSpearImage.setOnMouseClicked(event -> handleWeaponPurchase(playerController, battle, imageToLaneRelations.get(towers.get(index)), 2, insufficientResources, invalidLane, labelsContainer, Grid, index, 1, SniperCannon.class, titansAlive, imageToTitanRelation, battleLanes, imageToLaneRelations, root, Controller));
			wallSpreadCannonImage.setOnMouseClicked(event -> handleWeaponPurchase(playerController, battle, imageToLaneRelations.get(towers.get(index)), 3, insufficientResources, invalidLane, labelsContainer, Grid, index, 2, VolleySpreadCannon.class, titansAlive, imageToTitanRelation, battleLanes, imageToLaneRelations, root, Controller));
			proximityTrapImage.setOnMouseClicked(event -> handleWeaponPurchase(playerController, battle, imageToLaneRelations.get(towers.get(index)), 4, insufficientResources, invalidLane, labelsContainer, Grid, index, 3, WallTrap.class, titansAlive, imageToTitanRelation, battleLanes, imageToLaneRelations, root, Controller));
		}




		//creating the lanes containers

		for(int i=0;i<battle.getLanes().size();i++) {
			Pane lane = new Pane();
			lane.setPrefSize(1000, 200);
			Grid.add(lane, 2, i);

		}

		if(battle.isGameOver()) {
			AnchorPane gameOverContainer = new AnchorPane();
			Label gameOverText = new Label("GAME OVER!\n"
					+ "score: "+ battle.getScore());
			Button mainMenuButton = new Button("Play Again!");
			mainMenuButton.setLayoutX(600);
			mainMenuButton.setLayoutY(345);
			mainMenuButton.setPrefHeight(100);
			mainMenuButton.setPrefWidth(200);
			gameOverContainer.getChildren().addAll(gameOverText,mainMenuButton);
			AnchorPane.setLeftAnchor(mainMenuButton, 600.0);
			root.getChildren().clear();
			root.getChildren().add(gameOverContainer);

			mainMenuButton.setOnAction(e3 -> {
				Controller.switchToMainScene(e3);
			});

		}

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		// simulateGame.setOnAction(e-> {
		// 	Battle simBattle = battle.clone();
		// 	try {
		// 		simBattle.purchaseWeapon(2, (Lane) simBattle.getLanes().toArray()[simBattle.getLanes().toArray().length-1]);
		// 		simBattle.purchaseWeapon(2, (Lane) simBattle.getLanes().toArray()[simBattle.getLanes().toArray().length-1]);
		// 		simBattle.purchaseWeapon(2, (Lane) simBattle.getLanes().toArray()[simBattle.getLanes().toArray().length-1]);
		// 		simBattle.purchaseWeapon(2, (Lane) simBattle.getLanes().toArray()[simBattle.getLanes().toArray().length-1]);
		// 		simBattle.purchaseWeapon(2, (Lane) simBattle.getLanes().toArray()[simBattle.getLanes().toArray().length-1]);
		// 	} catch (InsufficientResourcesException | InvalidLaneException e2) {
		// 		// TODO Auto-generated catch block
		// 		e2.printStackTrace();
		// 	}
		// 	System.out.println(simBattle.getScore());
		// 	for(int i=0; i<10 ;i++) {
		// 		try {
		// 			simBattle = model2.simulateEasy(simBattle, 1);
		// 		} catch (IOException e1) {
		// 			// TODO Auto-generated catch block
		// 			e1.printStackTrace();
		// 		}
		// 	}
		// 	System.out.println(simBattle.getScore());


		// 	AnchorPane gameOverContainer = new AnchorPane();
		// 	Label gameOverText = new Label("GAME OVER!\n"
		// 			+ "score: "+ simBattle.getScore()+"\n"
		// 			+ "Turns: " + simBattle.getNumberOfTurns());
		// 	Button mainMenuButton = new Button("Play Again!");
		// 	mainMenuButton.setLayoutX(600);
		// 	mainMenuButton.setLayoutY(345);
		// 	mainMenuButton.setPrefHeight(100);
		// 	mainMenuButton.setPrefWidth(200);
		// 	gameOverContainer.getChildren().addAll(gameOverText,mainMenuButton);
		// 	AnchorPane.setLeftAnchor(mainMenuButton, 600.0);
		// 	root.getChildren().clear();
		// 	root.getChildren().add(gameOverContainer);

		// 	mainMenuButton.setOnAction(e3 -> {
		// 		Controller.switchToMainScene(e3);
		// 	});


		// });
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		//		simulateGame.setOnAction(e-> {
		//			while(!battle.isGameOver()) {
		//				Move move = null;
		//				try {
		//					move = model.getBestMove(battle);
		//				} catch (InsufficientResourcesException | InvalidLaneException e1) {
		//					e1.printStackTrace();
		//					break;
		//				}
		//				if(move.getPass()==true) {
		//					battle.passTurn();
		//					continue;
		//				} else {
		//					Lane targetLane = battle.getOriginalLanes().get(move.getLaneNumber());
		//					try {
		//						battle.purchaseWeapon(move.getWeaponCode(), targetLane);
		//					} catch (InsufficientResourcesException | InvalidLaneException e1) {
		//						e1.printStackTrace();
		//						break;
		//					}
		//				}
		//			}
		//
		//			if(battle.isGameOver()) {
		//				AnchorPane gameOverContainer = new AnchorPane();
		//				Label gameOverText = new Label("GAME OVER!\n"
		//						+ "score: "+ battle.getScore()+"\n"
		//								+ "Turns: " + battle.getNumberOfTurns());
		//				Button mainMenuButton = new Button("Play Again!");
		//				mainMenuButton.setLayoutX(600);
		//				mainMenuButton.setLayoutY(345);
		//				mainMenuButton.setPrefHeight(100);
		//				mainMenuButton.setPrefWidth(200);
		//				gameOverContainer.getChildren().addAll(gameOverText,mainMenuButton);
		//				AnchorPane.setLeftAnchor(mainMenuButton, 600.0);
		//				root.getChildren().clear();
		//				root.getChildren().add(gameOverContainer);
		//
		//				mainMenuButton.setOnAction(e3 -> {
		//					Controller.switchToMainScene(e3);
		//				});
		//			}
		//
		//
		//		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////

		//handling passTurn
		passTurn.setOnAction(e -> {

			if(battle.isGameOver()) {
				AnchorPane gameOverContainer = new AnchorPane();
				Label gameOverText = new Label("GAME OVER!\n"
						+ "score: "+ battle.getScore());
				Button mainMenuButton = new Button("Play Again!");
				mainMenuButton.setLayoutX(600);
				mainMenuButton.setLayoutY(345);
				mainMenuButton.setPrefHeight(100);
				mainMenuButton.setPrefWidth(200);
				gameOverContainer.getChildren().addAll(gameOverText,mainMenuButton);
				AnchorPane.setLeftAnchor(mainMenuButton, 600.0);
				root.getChildren().clear();
				root.getChildren().add(gameOverContainer);

				mainMenuButton.setOnAction(e3 -> {
					Controller.switchToMainScene(e3);
				});

			}

			if(battle.getLanes().size()<imageToLaneRelations.size()) {
				for(int i=0;i<battle.getOriginalLanes().size();i++) {
					if(imageToLaneRelations.get(getNodeFromGridPane(Grid,0,i))!=null && imageToLaneRelations.get(getNodeFromGridPane(Grid,0,i)).isLaneLost()) {
						((ImageView)(((VBox) getNodeFromGridPane(Grid,0,i)).getChildren()).get(2)).setImage(new Image("/lostTower.png"));
						imageToLaneRelations.remove(getNodeFromGridPane(Grid,0,i));
						i=0;
					}
				}
			}

			//creating titanImages

			for(int i = 0;i<battle.getNumberOfTitansPerTurn();i++) {


				if(battle.getApproachingTitans().isEmpty()) {
					battle.refillApproachingTitans();
				}
				Titan titan = battle.getApproachingTitans().get(0);
				VBox titanContainer = new VBox();
				Label titanHealth = new Label();
				titanHealth.textProperty().bind(Bindings.concat("HP: ", titan.getCurrentHealthIntegerProperty()));
				ImageView titanImageView = null;
				String titanClassName = titan.getClass().getName();

				switch (titanClassName) {
				case "game.engine.titans.AbnormalTitan":
					titanImageView = new ImageView(new Image("/abnormalTitan.png"));
					break;
				case "game.engine.titans.ArmoredTitan":
					titanImageView = new ImageView(new Image("/armoredTitan.png"));
					break;
				case "game.engine.titans.ColossalTitan":
					titanImageView = new ImageView(new Image("/colossalTitan.png"));
					break;
				case "game.engine.titans.PureTitan":
					titanImageView = new ImageView(new Image("/pureTitan.png"));
					break;
				default:
				}


				titanImageView.setFitHeight(titan.getHeightInMeters()<60?(titan.getHeightInMeters()*2)+50:100);
				titanImageView.setFitWidth(titan.getHeightInMeters()<60?(titan.getHeightInMeters()*2)+50:100);
				imageToTitanRelation.put(titanImageView, battle.getApproachingTitans().get(0));

				int loc = 0;
				while (loc < 5) {
					if (battle.getLanes().peek() == imageToLaneRelations.get(getNodeFromGridPane(Grid, 0, loc))) {
						break;
					}
					loc++;
				}

				titansAlive.add(titanContainer);
				Pane currLane = getNodeFromGridPane(Grid,2,loc);
				if((imageToLaneRelations.get(getNodeFromGridPane(Grid,0,loc)) !=null) && !imageToLaneRelations.get(getNodeFromGridPane(Grid,0,loc)).isLaneLost()) {
					titanContainer.getChildren().addAll(titanImageView, titanHealth);
					titanContainer.setLayoutX(currLane.getWidth() - 200);
					currLane.getChildren().add(titanContainer);
				}
			}

			playerController.handlePassTurnButton(battle);
			updateLabels(labelsContainer, battle);

			//moving titanImages

			if(!battle.isGameOver()) {
				for(int i=0; i<titansAlive.size();i++) {
					if(imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).isDefeated()) {
						((VBox)titansAlive.get(i)).setVisible(false);
						continue;
					}
					if(imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).hasReachedTarget()) continue;

					if(imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).titanLane(battle, battleLanes) == null|| imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).titanLane(battle, battleLanes).isLaneLost()) continue;


					titansAlive.get(i).setLayoutX(imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).getDistance());
				}
			}

		});
		return scene;
	}

	private <T> T getNodeFromGridPane(GridPane gridPane, int col, int row) {
		for (javafx.scene.Node node : gridPane.getChildren()) {
			if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row &&
					GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == col) {
				return (T) node;
			}
		}
		return null;
	}

	private void updateLabels(VBox labelsContainer, Battle battle) {
		((Label) labelsContainer.getChildren().get(0)).setText("Current score: " + battle.getScore());
		((Label) labelsContainer.getChildren().get(1)).setText("Current Turn: " + battle.getNumberOfTurns());
		((Label) labelsContainer.getChildren().get(2)).setText("Current Resources: " + battle.getResourcesGathered());
		((Label) labelsContainer.getChildren().get(3)).setText("Available Lanes: " + battle.getLanes().size());
		((Label) labelsContainer.getChildren().get(4)).setText("CurrentPhase " + battle.getBattlePhase().toString());
	}

	private <T extends Weapon> int countWeapons(ArrayList<Weapon> weapons, Class<T> weaponClass) {
		int count = 0;
		for (Weapon weapon : weapons) {
			if (weaponClass.isInstance(weapon)) {
				count++;
			}
		}
		return count;
	}

	private void updateWeaponCount(GridPane grid, int row, int weaponIndex, int count) {
		((Label)((HBox)((VBox)getNodeFromGridPane(grid, 1, row)).getChildren().get(weaponIndex)).getChildren().get(1)).setText(String.valueOf(count));
	}

	private void handleWeaponPurchase(PlayerController playerController, Battle battle, Lane lane, int weaponId, Alert insufficientResources, Alert invalidLane, VBox labelsContainer, GridPane Grid, int rowIndex, int weaponIndex, Class<? extends Weapon> weaponClass, ArrayList<VBox> titansAlive, HashMap<ImageView,Titan> imageToTitanRelation, ArrayList<Lane> battleLanes, HashMap<VBox, Lane> imageToLaneRelations, AnchorPane root, SceneController Controller) {
		try {

			if(battle.isGameOver()) {
				AnchorPane gameOverContainer = new AnchorPane();
				Label gameOverText = new Label("GAME OVER!\n"
						+ "score: "+ battle.getScore());
				Button mainMenuButton = new Button("Play Again!");
				mainMenuButton.setLayoutX(600);
				mainMenuButton.setLayoutY(345);
				mainMenuButton.setPrefHeight(100);
				mainMenuButton.setPrefWidth(200);
				gameOverContainer.getChildren().addAll(gameOverText,mainMenuButton);
				AnchorPane.setLeftAnchor(mainMenuButton, 600.0);
				root.getChildren().clear();
				root.getChildren().add(gameOverContainer);

				mainMenuButton.setOnAction(e3 -> {
					Controller.switchToMainScene(e3);
				});

			}

			if(battle.getLanes().size()<imageToLaneRelations.size()) {
				for(int i=0;i<battle.getOriginalLanes().size();i++) {
					if(imageToLaneRelations.get(getNodeFromGridPane(Grid,0,i))!=null && imageToLaneRelations.get(getNodeFromGridPane(Grid,0,i)).isLaneLost()) {
						((ImageView)(((VBox) getNodeFromGridPane(Grid,0,i)).getChildren()).get(2)).setImage(new Image("/lostTower.png"));
						imageToLaneRelations.remove(getNodeFromGridPane(Grid,0,i));
						i=0;
					}
				}
			}


			//creating titanImages

			for(int i = 0;i<battle.getNumberOfTitansPerTurn();i++) {


				if(battle.getApproachingTitans().isEmpty()) {
					battle.refillApproachingTitans();
				}
				Titan titan = battle.getApproachingTitans().get(0);
				VBox titanContainer = new VBox();
				Label titanHealth = new Label();
				titanHealth.textProperty().bind(Bindings.concat("HP: ", titan.getCurrentHealthIntegerProperty()));
				ImageView titanImageView = null;
				String titanClassName = titan.getClass().getName();

				switch (titanClassName) {
				case "game.engine.titans.AbnormalTitan":
					titanImageView = new ImageView(new Image("/abnormalTitan.png"));
					break;
				case "game.engine.titans.ArmoredTitan":
					titanImageView = new ImageView(new Image("/armoredTitan.png"));
					break;
				case "game.engine.titans.ColossalTitan":
					titanImageView = new ImageView(new Image("/colossalTitan.png"));
					break;
				case "game.engine.titans.PureTitan":
					titanImageView = new ImageView(new Image("/pureTitan.png"));
					break;
				default:
				}


				titanImageView.setFitHeight(titan.getHeightInMeters()<60?(titan.getHeightInMeters()*2)+50:100);
				titanImageView.setFitWidth(titan.getHeightInMeters()<60?(titan.getHeightInMeters()*2)+50:100);
				imageToTitanRelation.put(titanImageView, battle.getApproachingTitans().get(0));

				int loc = 0;
				while (loc < 5) {
					if (battle.getLanes().peek() == imageToLaneRelations.get(getNodeFromGridPane(Grid, 0, loc))) {
						break;
					}
					loc++;
				}

				titansAlive.add(titanContainer);
				Pane currLane = getNodeFromGridPane(Grid,2,loc);
				if((imageToLaneRelations.get(getNodeFromGridPane(Grid,0,loc)) !=null) && !imageToLaneRelations.get(getNodeFromGridPane(Grid,0,loc)).isLaneLost()) {
					titanContainer.getChildren().addAll(titanImageView, titanHealth);
					titanContainer.setLayoutX(currLane.getWidth() - 200);
					currLane.getChildren().add(titanContainer);
				}
			}

			playerController.handleBuyButton(battle, weaponId, lane);
			updateLabels(labelsContainer, battle);
			int newCount = countWeapons(lane.getWeapons(), weaponClass);
			updateWeaponCount(Grid, rowIndex, weaponIndex, newCount);



			//moving titanImages	
			if(!battle.isGameOver()) {
				for(int i=0; i<titansAlive.size();i++) {
					if(imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).isDefeated()) {
						((VBox)titansAlive.get(i)).setVisible(false);
						continue;
					}
					if(imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).hasReachedTarget()) continue;

					if(imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).titanLane(battle, battleLanes) == null|| imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).titanLane(battle, battleLanes).isLaneLost()) continue;


					titansAlive.get(i).setLayoutX(imageToTitanRelation.get(titansAlive.get(i).getChildren().get(0)).getDistance());
				}
			}


		} catch (InsufficientResourcesException | InvalidLaneException e) {
			if (e instanceof InsufficientResourcesException) insufficientResources.showAndWait();
			if (e instanceof InvalidLaneException) invalidLane.showAndWait();
			e.printStackTrace();
		}
	}
}