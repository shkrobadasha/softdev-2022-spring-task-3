package com.example.battleship;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        SceneController sceneController = SceneController.getInstance();
        sceneController.startScene(stage, SceneController.State.FIRST);
    }
}