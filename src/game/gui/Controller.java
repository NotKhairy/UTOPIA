package game.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class Controller implements Initializable{
	
	@FXML
    private ChoiceBox<String> choiceBox;
	
	public void initialize(URL location, ResourceBundle resources) {
        choiceBox.getItems().addAll("Easy", "Hard");
    }
	
}
