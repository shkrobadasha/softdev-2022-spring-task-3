package com.example.battleship;

import com.example.battleship.controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) {
        SceneController.getInstance().startScene(stage, SceneController.State.REGISTRATION);
    }
}
