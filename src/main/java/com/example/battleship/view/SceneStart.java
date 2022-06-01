package com.example.battleship.view;

import com.example.battleship.Main;
import com.example.battleship.controller.BattleShipExceptionHandler;
import com.example.battleship.controller.Game;
import com.example.battleship.controller.BattleShipException;
import com.example.battleship.controller.SceneController;
import com.example.battleship.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class SceneStart implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField firatnamefiels;
    @FXML
    private TextField secondnamefield;
    @FXML
    private ChoiceBox<String> gamemode;
    @FXML
    private Button startGame;

    private String firstPlayer;
    private String secondPlayer;
    private Game.Mode currentMode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (int i = 0; i < Game.Mode.values().length; i++) {
            gamemode.getItems().addAll(Game.Mode.values()[i].toString());
        }

        gamemode.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentMode = Game.Mode.values()[newValue.intValue()];
        });

        gamemode.setValue("Выберите режим");
        startGame.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showSecondScene());

        firatnamefiels.focusedProperty().addListener(listener -> firstPlayer = firatnamefiels.getText());
        secondnamefield.focusedProperty().addListener(listener -> secondPlayer = secondnamefield.getText());
    }

    private void showSecondScene() {
        try {
            Game game = SceneController.getInstance().getGame();
            game.setRegistrationData(
                    new Player[]{new Player(firstPlayer), new Player(secondPlayer)},
                    currentMode
            );
            SceneController.getInstance().startScene(SceneController.State.SETUP_GAME);
        } catch (BattleShipException.RegistrationException e) {
            BattleShipExceptionHandler.handle(e);
        }
    }
}
