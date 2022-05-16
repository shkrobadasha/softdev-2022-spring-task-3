package com.example.battleship.view;

import com.example.battleship.Main;
import com.example.battleship.controller.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private static Scene first;
    private static Scene second;
    private Scene third;
    private Stage currentStage;
    private final Game game = new Game();

    private void startScene(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.show();
    }


    public void startScene(State state) throws IOException {
        startScene(currentStage, state);
    }

    public void startScene(Stage stage, State state) throws IOException {
        currentStage = stage;

        Scene scene = switch (state) {
            case FIRST -> {
                first = new Scene(FXMLLoader.load(Main.class.getResource("layout_start.fxml")));
                yield first;
            }
            case SECOND -> {
                second = new Scene(FXMLLoader.load(Main.class.getResource("layout_setup.fxml")));
                yield second;
            }
            case THIRD -> third;
        };

        startScene(stage, scene);
    }

    public Game getGame() {
        return game;
    }

    public enum State {FIRST, SECOND, THIRD}
}
