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

	public void handleBuyButton(Battle battle, int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException {
		battle.purchaseWeapon(weaponCode, lane);
	}
	public void handlePassTurnButton(Battle battle) {
		battle.passTurn();
		
	}
}
