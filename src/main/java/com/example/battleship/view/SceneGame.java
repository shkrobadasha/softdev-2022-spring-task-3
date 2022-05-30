package com.example.battleship.view;

import com.example.battleship.Main;
import com.example.battleship.controller.Game;
import com.example.battleship.controller.util.ItemWrapper;
import com.example.battleship.model.Field;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.battleship.controller.util.CellColors.*;

public class SceneGame implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane enemyGridPane;

    @FXML
    private Label playerName;

    private static final double SIDE = 29.75;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Game game = Main.sceneController.getGame();

        GridController firstController = new GridController(gridPane);
        GridController secondController = new GridController(enemyGridPane);
        game.registerStrikeActionListeners(firstController, secondController);
    }

    private class GridController implements Field.StrikeAction {
        private final GridPane gridPane;
        private boolean isEnable;
        private final ArrayList<ItemWrapper<Rectangle>> cells = new ArrayList<>();

        public GridController(GridPane gridPane) {
            this.gridPane = gridPane;
            setupGridPane();
        }

        private void setupGridPane() {
            for (int x = 0; x < gridPane.getColumnCount(); x++) {
                for (int y = 0; y < gridPane.getRowCount(); y++) {

                    Paint paint = Paint.valueOf(EMPTY_COLOR);
                    Rectangle item = new Rectangle(SIDE, SIDE, paint);
                    ItemWrapper<Rectangle> itemWrapper = new ItemWrapper<>(item, x, y);

                    item.setOnMouseEntered(event -> select(item));
                    item.setOnMouseExited(event -> unselect(item));
                    item.setOnMouseClicked(event -> strike(itemWrapper));

                    cells.add(itemWrapper);
                    gridPane.add(item, x, y);
                }
            }
        }

        @Override
        public void onStrike(int index, Result result) {

            String color = switch (result) {
                case SHIP -> SHIP_COLOR;
                case MINE -> MINE_COLOR;
                case MISS -> MISS_COLOR;
            };

            if (Main.sceneController.getGame().getCurrentMode() == Game.Mode.SALVO_BLIND) {
                color = PRE_SHIP_COLOR;
            }

            paintItem(cells.get(index).getItem(), color);
        }

        @Override
        public void setEnable(boolean isEnable) {
            if (isEnable) {
                Game game = Main.sceneController.getGame();
                playerName.setText(game.getCurrentPlayer().getName());
            }
            this.isEnable = isEnable;
        }

        private void strike(ItemWrapper<Rectangle> itemWrapper) {
            if (!isEnable || itemWrapper.isUsed()) {
                return;
            }
            Game game = Main.sceneController.getGame();

            if (game.getCurrentMode() != Game.Mode.CLASSIC) {
                paintItem(itemWrapper.getItem(), PRE_SHIP_COLOR);
            }

            itemWrapper.setUsed(true);
            Main.sceneController.getGame().addStrike(itemWrapper.getIndex());
        }

        private void select(Rectangle rectangle) {
            if (!isEnable || !rectangle.getFill().equals(Paint.valueOf(EMPTY_COLOR))) {
                return;
            }

            paintItem(rectangle, SELECT_SHIP_COLOR);
        }

        private void unselect(Rectangle rectangle) {
            if (!isEnable || !rectangle.getFill().equals(Paint.valueOf(SELECT_SHIP_COLOR))) {
                return;
            }

            paintItem(rectangle, EMPTY_COLOR);
        }

        private void paintItem(Rectangle rectangle, String color) {
            rectangle.setFill(Paint.valueOf(color));
        }
    }
}
