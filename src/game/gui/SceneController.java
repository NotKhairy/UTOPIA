package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class SceneController{
	
	private Stage stage;
	private Scene scene;
	
	public void switchToBattleScene(ActionEvent event, String s) throws IOException {
		BattleScene BattleScene = new BattleScene();
		Scene battlescene = BattleScene.createScene(s);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = battlescene;
		stage.setScene(scene);
		stage.show();
	}
	
}
