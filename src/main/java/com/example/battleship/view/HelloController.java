package com.example.battleship.view;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {

    private static final double SIDE = 29;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane grid_pane;

    @FXML
    void initialize() {

        for (int i = 0; i < grid_pane.getRowCount(); i++) {

            for (int j = 0; j < grid_pane.getRowCount(); j++) {
                Paint paint = Paint.valueOf(Color.GRAY.toString());

                Rectangle rectangle = new Rectangle(SIDE, SIDE, paint);
                rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    rectangle.setFill(Paint.valueOf(Color.RED.toString()));
                });
//                Button button = new Button("" + j);
//                grid_pane.add(button, i, j);
                grid_pane.add(rectangle, i, j);
            }

        }
    }

}