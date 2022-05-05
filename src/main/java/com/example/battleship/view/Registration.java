package com.example.battleship.view;

import com.example.battleship.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Registration {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firatnamefiels;

    @FXML
    private ChoiceBox<?> gamemode;

    @FXML
    private TextField secondnamefield;

    @FXML
    private Button startGame;

    @FXML
    void initialize() {
        startGame.setOnAction(event -> {
            System.out.println("Working");
        });
        /*assert firatnamefiels != null : "fx:id=\"firatnamefiels\" was not injected: check your FXML file 'battleship.fxml'.";
        assert gamemode != null : "fx:id=\"gamemode\" was not injected: check your FXML file 'battleship.fxml'.";
        assert secondnamefield != null : "fx:id=\"secondnamefield\" was not injected: check your FXML file 'battleship.fxml'.";
        assert startGame != null : "fx:id=\"startGame\" was not injected: check your FXML file 'battleship.fxml'.";*/

    }

}

