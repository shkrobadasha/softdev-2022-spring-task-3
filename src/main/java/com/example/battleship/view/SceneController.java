package com.example.battleship.view;

import com.example.battleship.Main;
import com.example.battleship.controller.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private Stage currentStage;
    private final Game game = new Game();

    public void startScene(State state) {
        startScene(currentStage, state);
    }

    public void startScene(Stage stage, State state) {
        currentStage = stage;

        try {
            Scene scene;
            switch (state) {
                case REGISTRATION:
                    scene = new Scene(FXMLLoader.load(Main.class.getResource("layout_start.fxml")));
                    break;
                case SETUP_GAME:
                    scene= new Scene(FXMLLoader.load(Main.class.getResource("layout_setup.fxml")));
                    break;
                case GAME:
                    scene = new Scene(FXMLLoader.load(Main.class.getResource("layout_game.fxml")));
                    break;
                default: /*FINISH_GAME*/
                    scene = new Scene(FXMLLoader.load(Main.class.getResource("layout_finish.fxml")));
            };

            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Game getGame() {
        return game;
    }

    public enum State {
        REGISTRATION,
        SETUP_GAME,
        GAME,
        FINISH_GAME
    }
}
