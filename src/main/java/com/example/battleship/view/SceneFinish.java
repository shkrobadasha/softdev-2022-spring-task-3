package com.example.battleship.view;

import com.example.battleship.Main;
import com.example.battleship.controller.SceneController;
import com.example.battleship.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SceneFinish implements Initializable {

    @FXML
    private Label playerName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Player winner = SceneController.getInstance().getGame().getWinner();
        playerName.setText(winner.getName());
    }

}
