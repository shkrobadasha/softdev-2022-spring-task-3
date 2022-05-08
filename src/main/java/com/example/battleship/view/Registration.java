package com.example.battleship.view;

import com.example.battleship.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Registration implements Initializable {

    private final String[] mode = {"classic", "salvo", "salvo blind"};
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField firatnamefiels;
    @FXML
    private ChoiceBox<String> gamemode;
    @FXML
    private TextField secondnamefield;

    @FXML
    private Button startGame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamemode.getItems().addAll(mode);

        startGame.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showSecondScene());
    }

    private void showSecondScene() {
        try {
            SceneController.getInstance()
                    .startScene(SceneController.State.SECOND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

