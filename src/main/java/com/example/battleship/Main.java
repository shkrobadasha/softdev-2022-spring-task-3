package com.example.battleship;

import com.example.battleship.view.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static SceneController sceneController = new SceneController();

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        sceneController.startScene(stage, SceneController.State.FIRST);
    }
}