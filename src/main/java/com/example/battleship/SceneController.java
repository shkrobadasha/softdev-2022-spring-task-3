package com.example.battleship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private static SceneController instance;
    private Scene first;
    private Scene second;
    private Scene third;
    private Stage currentStage;

    private SceneController() throws IOException {
        first = new Scene(FXMLLoader.load(Main.class.getResource("battleship.fxml")));
        second = new Scene(FXMLLoader.load(Main.class.getResource("hello-view.fxml")));
    }

    public static SceneController getInstance() throws IOException {
        if (instance == null)
            instance = new SceneController();

        return instance;
    }

    private void startScene(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    public void startScene(State state) {
        startScene(currentStage, state);
    }
    public void startScene(Stage stage, State state) {
        currentStage = stage;

        Scene scene = switch (state) {
            case FIRST -> first;
            case SECOND -> second;
            case THIRD -> third;
        };

        startScene(stage,scene);
    }

    public enum State {FIRST, SECOND, THIRD}
}
