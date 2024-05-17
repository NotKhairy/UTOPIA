package game.gui;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class PlayerController {
	
	private Scene scene;
	
	public void handleBuyButton(Battle battle, int weaponCode, Lane lane, Node root) throws InsufficientResourcesException, InvalidLaneException {
		battle.purchaseWeapon(weaponCode, lane);
		refreshData(root);
	}
	public void handlePassTurnButton(Battle battle, Node root) {
		battle.passTurn();
		refreshData(root);
		
	}
	private void refreshData(Node root) {
		// TODO Auto-generated method stub
		
	}
}
