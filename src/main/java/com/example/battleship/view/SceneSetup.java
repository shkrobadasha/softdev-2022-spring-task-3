package com.example.battleship.view;

import com.example.battleship.controller.BattleShipExceptionHandler;
import com.example.battleship.controller.Game;
import com.example.battleship.controller.Game.Items;
import com.example.battleship.controller.util.ItemWrapper;
import com.example.battleship.controller.util.SpaceFinder;
import com.example.battleship.controller.util.UsedIndexFinder;
import com.example.battleship.model.BattleShipException;
import com.example.battleship.model.Field;
import com.example.battleship.model.ships.AbstractShip;
import com.example.battleship.model.ships.implementation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.example.battleship.Main.sceneController;
import static com.example.battleship.controller.util.CellColors.*;

public class SceneSetup {
    private static final double SIDE = 29.75;

    @FXML
    private Label cruise;

    @FXML
    private Button cruiserAdd;

    @FXML
    private Label cruiserCount;

    @FXML
    private Label destroyer;

    @FXML
    private Button destroyerAdd;

    @FXML
    private Label destroyerCount;

    @FXML
    private GridPane grid_pane;

    @FXML
    private Label lincorn;

    @FXML
    private Button lincornAdd;

    @FXML
    private Label lincornCount;

    @FXML
    private Label mine;

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
    private Label submarine;

    @FXML
    private Button submarineAdd;

    @FXML
    private Label submarineCount;

    private boolean isRemove = false;
    private int currentShipSize;
    private Items currentShipItem;
    private HashMap<Items, Integer> shipCounts = getClearShipCounts();

    private HashMap<Items, Integer> getClearShipCounts() {
        return new HashMap<>() {{
            put(Items.LINCORN, 0);
            put(Items.CRUISER, 0);
            put(Items.DESTROYER, 0);
            put(Items.SUBMARINE, 0);
            put(Items.MINE, 0);
        }};
    }

    private final ArrayList<ItemWrapper<Rectangle>> cells = new ArrayList<>();
    private final ArrayList<AbstractShip> ships = new ArrayList<>();//список всех кораблей,
    // которые возможно разместить
    private boolean isVertical = false;

    @FXML
    public void initialize() {
        showPlayerName(0);
        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                createField(ships);
                startGameIfReady();
            } catch (BattleShipException.HasUnusedShipException e) {
                BattleShipExceptionHandler.handle(e);
            }
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

                    if (isRemove) {
                        remove(itemWrapper);
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        rotate(itemWrapper);
                    } else {
                        safetyAddItem(itemWrapper);
                    }
                });

                cells.add(itemWrapper);
                grid_pane.add(item, x, y);
            }
        }
    }

    private void startGameIfReady() {
        if (sceneController.getGame().isReady()) {
            sceneController.startScene(SceneController.State.GAME);
        } else {
            clearScene();
            showPlayerName(1);
        }
    }

    private void createField(ArrayList<AbstractShip> ships) throws BattleShipException.HasUnusedShipException {
        if (ships.size() != Items.getTotalCount()) {
            throw new BattleShipException.HasUnusedShipException();
        }

        Field field = new Field();
        for (AbstractShip ship : ships) {
            field.addShip(ship);
        }

        sceneController.getGame().addFields(field);
    }

    private void clearScene() {
        String countStr = "0";
        lincornCount.setText(countStr);
        cruiserCount.setText(countStr);
        destroyerCount.setText(countStr);
        submarineCount.setText(countStr);
        mineCount.setText(countStr);

        shipCounts = getClearShipCounts();
        ships.clear();

        for (ItemWrapper<Rectangle> cell : cells) {
            cell.setUsed(false);
            cell.getItem().setFill(Paint.valueOf(EMPTY_COLOR));
        }

        currentShipSize = 0;
    }

    private void safetyAddItem(ItemWrapper<Rectangle> itemWrapper) {
        try {
            addItem(itemWrapper);
        } catch (BattleShipException.SetupException e) {
            BattleShipExceptionHandler.handle(e);
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

        stateButton.setSelected(false);
        setRemoveMode(stateButton.isSelected());

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

        AbstractShip ship = findShipByIndex(itemWrapper.getIndex());
        ArrayList<Integer> indexes = ship.getIndexes();
        ships.remove(ship);

        for (int i : indexes) {
            ItemWrapper<Rectangle> currentItemWrapper = cells.get(i);
            currentItemWrapper.setUsed(false);
            paintItem(currentItemWrapper.getItem(), EMPTY_COLOR);
        }

        Items shipItem = Items.getItemFromShip(ship);
        int count = shipCounts.get(shipItem);
        shipCounts.put(shipItem, --count);

        String countStr = Integer.toString(count);
        switch (shipItem) {
            case LINCORN -> lincornCount.setText(countStr);
            case CRUISER -> cruiserCount.setText(countStr);
            case DESTROYER -> destroyerCount.setText(countStr);
            case SUBMARINE -> submarineCount.setText(countStr);
            case MINE -> mineCount.setText(countStr);
        }
    }

    private AbstractShip findShipByIndex(int index) {

        for (AbstractShip ship : ships) {
            if (ship.hasCells(index)) {
                return ship;
            }
        }

        throw new IllegalArgumentException();
    }

    private void addItem(ItemWrapper<Rectangle> itemWrapper) throws BattleShipException.SetupException {
        if (currentShipSize == 0) return;
        if (itemWrapper.isUsed()) return;

        checkCount(currentShipItem);

        ArrayList<Integer> indexItemsToCLick = SpaceFinder.findSpaceFromItemWrapper(
                cells, itemWrapper.getIndex(), 10, 10, currentShipSize, isVertical
        );
        boolean hasNeighbour = UsedIndexFinder.hasUsedNeighbourFromItemWrapper(cells, new HashSet<>() {{
            addAll(indexItemsToCLick);
        }}, 10, 10);

        if (hasNeighbour || indexItemsToCLick.isEmpty()) throw new BattleShipException.ItemSpaceException();

        for (int index : indexItemsToCLick) {
            ItemWrapper<Rectangle> currentItemWrapper = cells.get(index);
            currentItemWrapper.setUsed(true);
            String color = SHIP_COLOR;

            if (currentShipItem == Items.MINE) {
                color = MINE_COLOR;
            }

            paintItem(currentItemWrapper.getItem(), color);
        }

        ships.add(createShip(currentShipItem, indexItemsToCLick));

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
    }

    private AbstractShip createShip(Items currentShipItem, ArrayList<Integer> indexes) {
        int[] indexArray = new int[indexes.size()];

        for (int i = 0; i < indexes.size(); i++) {
            indexArray[i] = indexes.get(i);
        }

        return switch (currentShipItem) {
            case LINCORN -> new Lincorn(indexArray);
            case CRUISER -> new Cruiser(indexArray);
            case DESTROYER -> new Destroyer(indexArray);
            case SUBMARINE -> new Submarine(indexArray);
            case MINE -> new Mine(indexArray);
        };
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
                cells, itemWrapper.getIndex(), 10, 10, currentShipSize, isVertical
        );
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
