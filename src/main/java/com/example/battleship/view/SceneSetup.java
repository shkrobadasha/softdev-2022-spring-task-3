package com.example.battleship.view;

import com.example.battleship.controller.BattleShipExceptionHandler;
import com.example.battleship.controller.Game;
import com.example.battleship.controller.Game.Items;
import com.example.battleship.controller.util.ItemWrapper;
import com.example.battleship.controller.util.SpaceFinder;
import com.example.battleship.controller.util.UsedIndexFinder;
import com.example.battleship.model.BattleShipException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.example.battleship.Main.sceneController;

public class SceneSetup {
    //TODO:
    //  1) Коллекция для item - повторяет грид
    //  2) Алгоритм размещения
    //  3) rotate
    //  4) add

    private static final double SIDE = 28.5;

    private static final String SHIP_COLOR = Color.DARKGREEN.toString();
    private static final String SELECT_SHIP_COLOR = Color.LIGHTGREEN.toString();
    private static final String EMPTY_COLOR = Color.WHITE.toString();

    @FXML
    private Button cruiserAdd;

    @FXML
    private Label cruiserCount;

    @FXML
    private Button destroyerAdd;

    @FXML
    private Label destroyerCount;

    @FXML
    private GridPane grid_pane;

    @FXML
    private Button lincornAdd;

    @FXML
    private Label lincornCount;

    @FXML
    private Button mineAdd;

    @FXML
    private Label mineCount;

    @FXML
    private Button nextButton;

    @FXML
    private Label playerName;

    @FXML
    private ToggleButton stateButton;

    @FXML
    private Button submarineAdd;

    @FXML
    private Label submarineCount;

    private boolean isRemove = false;
    private int currentShipSize;
    private Items currentShipItem;
    private HashMap<Items, Integer> shipCounts = new HashMap<>() {{
        put(Items.LINCORN, 0);
        put(Items.CRUISER, 0);
        put(Items.DESTROYER, 0);
        put(Items.SUBMARINE, 0);
        put(Items.MINE, 0);
    }};
    private ArrayList<ItemWrapper<Rectangle>> cells = new ArrayList<>();
    private boolean isVertical = false;

    @FXML
    public void initialize() {
        showPlayerName(0);
        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO
        });

        stateButton.setOnMouseClicked(event -> setRemoveMode(stateButton.isSelected()));
        lincornAdd.setOnMouseClicked(event -> setCurrentShipSize(Items.LINCORN));
        cruiserAdd.setOnMouseClicked(event -> setCurrentShipSize(Items.CRUISER));
        destroyerAdd.setOnMouseClicked(event -> setCurrentShipSize(Items.DESTROYER));
        submarineAdd.setOnMouseClicked(event -> setCurrentShipSize(Items.SUBMARINE));
        mineAdd.setOnMouseClicked(event -> setCurrentShipSize(Items.MINE));

        for (int x = 0; x < grid_pane.getColumnCount(); x++) {
            for (int y = 0; y < grid_pane.getRowCount(); y++) {

                Paint paint = Paint.valueOf(EMPTY_COLOR);
                Rectangle item = new Rectangle(SIDE, SIDE, paint);
                ItemWrapper<Rectangle> itemWrapper = new ItemWrapper<>(item, x, y);

                item.setOnMouseEntered(event -> select(itemWrapper));
                item.setOnMouseExited(event -> unselect(itemWrapper));
                item.setOnMouseClicked(event -> {

                    if (event.getButton() == MouseButton.SECONDARY && !isRemove) {
                        rotate(itemWrapper);

                    }else if (!isRemove) {
                        addItem(itemWrapper);
                    } else {
                        remove(itemWrapper);
                    }
                });

                cells.add(itemWrapper);
                grid_pane.add(item, x, y);
            }
        }
    }

    private void rotate(ItemWrapper<Rectangle> itemWrapper) {
        unselect(itemWrapper);
        isVertical = !isVertical;
        select(itemWrapper);
    }

    private void setCurrentShipSize(Items item) {
        try {
            checkCount(item);
        } catch (BattleShipException.ShipItemCountException e) {
            BattleShipExceptionHandler.handle(e);
        }

        currentShipSize = item.getSize();
        currentShipItem = item;
    }

    private void checkCount(Items item) throws BattleShipException.ShipItemCountException {
        if (shipCounts.get(item) == item.getCount()) {
            throw new BattleShipException.ShipItemCountException();
        }
    }

    private void remove(ItemWrapper<Rectangle> itemWrapper) {
        if (itemWrapper.getItem().getFill().equals(Paint.valueOf(EMPTY_COLOR))) {
            return;
        }

        itemWrapper.setUsed(false);
        int count = shipCounts.get(currentShipItem);
        shipCounts.put(currentShipItem, --count); // TODO поиск по количеству

        String countStr = Integer.toString(count);
        switch (currentShipItem) {
            case LINCORN -> lincornCount.setText(countStr);
            case CRUISER -> cruiserCount.setText(countStr);
            case DESTROYER -> destroyerCount.setText(countStr);
            case SUBMARINE -> submarineCount.setText(countStr);
            case MINE -> mineCount.setText(countStr);
        }

        paintItem(itemWrapper.getItem(), EMPTY_COLOR);
    }

    private void addItem(ItemWrapper<Rectangle> itemWrapper) {
        if (currentShipSize == 0) return;
        if (itemWrapper.isUsed()) return;

        try {
            checkCount(currentShipItem);
        } catch (BattleShipException.ShipItemCountException e) {
            BattleShipExceptionHandler.handle(e);
            return;
        }

        boolean hasNeighbour = UsedIndexFinder.hasUsedNeighbourFromItemWrapper(cells, new HashSet<>() {{
            add(itemWrapper.toIndex(10));
        }}, 10, 10);

        if (hasNeighbour) {
            BattleShipExceptionHandler.handle(new BattleShipException.ItemSpaceException());
            return;
        }

        itemWrapper.setUsed(true);
        int count = shipCounts.get(currentShipItem);
        shipCounts.put(currentShipItem, ++count);

        String countStr = Integer.toString(count);
        switch (currentShipItem) {
            case LINCORN -> lincornCount.setText(countStr);
            case CRUISER -> cruiserCount.setText(countStr);
            case DESTROYER -> destroyerCount.setText(countStr);
            case SUBMARINE -> submarineCount.setText(countStr);
            case MINE -> mineCount.setText(countStr);
        }

        paintItem(itemWrapper.getItem(), SHIP_COLOR);
    }

    private void showPlayerName(int i) {
        Game game = sceneController.getGame();
        playerName.setText(game.getPlayer(i).getName());
    }

    private void unselect(ItemWrapper<Rectangle> itemWrapper) {
        if (isRemove) return;

        ArrayList<Rectangle> selectedItems = getSelectedItems(itemWrapper);
        paintItems(selectedItems, EMPTY_COLOR);
    }

    private void select(ItemWrapper<Rectangle> itemWrapper) {
        if (isRemove) return;

        ArrayList<Rectangle> selectedItems = getSelectedItems(itemWrapper);
        paintItems(selectedItems, SELECT_SHIP_COLOR);
    }

    private ArrayList<Rectangle> getSelectedItems(ItemWrapper<Rectangle> itemWrapper) {
        ArrayList<Integer> result = SpaceFinder.findSpaceFromItemWrapper(
                cells, itemWrapper.toIndex(10), 10, 10, currentShipSize, isVertical);
        ArrayList<Rectangle> selectedItems = new ArrayList<>();

        for (int i : result) {
            selectedItems.add(cells.get(i).getItem());
        }

        return selectedItems;
    }

    private void setRemoveMode(Boolean remove) {
        isRemove = remove;
    }

    private void paintItems(ArrayList<Rectangle> rectangles, String color) {
        for (Rectangle r : rectangles) {
            paintItem(r, color);
        }
    }

    private void paintItem(Rectangle rectangle, String color) {
        if (!isRemove && rectangle.getFill().equals(Paint.valueOf(SHIP_COLOR))) {
            return;
        }

        rectangle.setFill(Paint.valueOf(color));
    }

}
