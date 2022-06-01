package com.example.battleship.controller;

import com.example.battleship.Main;
import com.example.battleship.controller.BattleShipException.ModeNotFoundException;
import com.example.battleship.controller.BattleShipException.PlayerNotFoundException;
import com.example.battleship.model.Field;
import com.example.battleship.model.Field.StrikeAction;
import com.example.battleship.model.Player;
import com.example.battleship.model.ships.AbstractShip;
import com.example.battleship.model.ships.implementation.*;
import com.example.battleship.view.NotificationUtil;

import java.util.ArrayList;
import java.util.List;

public class Game implements Field.FieldAction {

    private final List<Field> fields = new ArrayList<>();//для хранения 2 полей
    private final List<Integer> strikeIndexes = new ArrayList<>();//для текущих выстрелов
    private Player[] players;
    private Player winner;
    private Mode currentMode;
    private int moveCount = 1;//счетчик очередности

    public void setRegistrationData(Player[] players, Mode currentMode) throws BattleShipException.RegistrationException {
        setPlayers(players);
        setCurrentMode(currentMode);
    }

    private void setPlayers(Player[] players) throws PlayerNotFoundException {
        validatePlayers(players);
        this.players = players;
    }

    public Player getPlayer(int i) {
        return players[i];
    }

    public void addFields(Field field) {
        fields.add(field);
        field.setActionListener(this);
    }

    @Override //
    public void shipWasSunk(Field field) {
        int indexOfAnotherField = fields.indexOf(getAnotherField(field));
        NotificationUtil.notifyInfo(players[indexOfAnotherField].getName(), "Потопил корабль");
    }

    @Override
    public void mineWasSunk(Field field, int index) {
        Field anotherField = getAnotherField(field);

        int indexOfAnotherField = fields.indexOf(anotherField);
        NotificationUtil.notifyInfo(players[indexOfAnotherField].getName(), "Потопил мину");
        anotherField.strike(index, false);
    }

    @Override
    public void changeTurn(Field field) {
        moveCount++;
        field.setEnable(false);
        getAnotherField(field).setEnable(true);
        strikeIndexes.clear();
    }

    @Override
    public void finishGame(Field field) {
        int indexField = fields.indexOf(getAnotherField(field));
        if (indexField == -1) {
            return;
        }

        winner = players[indexField];
        NotificationUtil.notifyInfo(winner.getName(), "Победил");
        SceneController.getInstance().startScene(SceneController.State.FINISH_GAME);
    }

    private void validateCurrentMode(Mode currentMode) throws ModeNotFoundException {
        if (currentMode == null) throw new ModeNotFoundException();
    }

    private void validatePlayers(Player[] players) throws PlayerNotFoundException {
        if (players == null || players.length != 2) throw new PlayerNotFoundException();

        for (Player p : players) {
            if (p == null || p.getName() == null || p.getName().isEmpty()) throw new PlayerNotFoundException();
        }
    }

    public boolean isReady() {
        return (players != null && currentMode != null && fields.size() == 2);
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentPlayer() {
        return players[(moveCount - 1) % 2];
    }

    public void addStrike(int index) {
        strikeIndexes.add(index);

        Field field = getCurrentField();
        int countStrikes = 1;

        if (currentMode != Mode.CLASSIC) {
            countStrikes = getAnotherField(field).getMaxSizeOfAliveShip();
        }

        applyStrikes(field, countStrikes);
    }

    private void applyStrikes(Field field, int countStrikes) {
        if (strikeIndexes.size() < countStrikes)
            return;

        ArrayList<Integer> currentStrikeIndexes = new ArrayList<>(strikeIndexes);

        ArrayList<StrikeAction.Result> currentStrikeResult = new ArrayList<>();
        boolean nonClassic = currentMode == Mode.CLASSIC;

        for (int i : currentStrikeIndexes) {
            StrikeAction.Result result = field.strike(i, nonClassic);

            if (result != StrikeAction.Result.EMPTY && result != StrikeAction.Result.MISS) {
                currentStrikeResult.add(result);
            }
        }

        strikeIndexes.clear();

        if (!nonClassic &&
                (!currentStrikeResult.contains(StrikeAction.Result.SHIP)
                        || currentStrikeResult.contains(StrikeAction.Result.MINE))) {
            changeTurn(field);
        }
    }

    public void registerStrikeActionListeners(StrikeAction forFirstField, StrikeAction forSecondField) {
        forSecondField.setEnable(true);
        fields.get(0).setStrikeActionListener(forFirstField);
        fields.get(1).setStrikeActionListener(forSecondField);
    }

    private Field getCurrentField() {
        return getField(moveCount);
    }

    private Field getAnotherField(Field currentField) {
        int indexField = fields.indexOf(currentField);

        if (indexField == -1) throw new IllegalArgumentException();

        int index = -1;

        for (int i = 0; i < fields.size(); i++) {
            if (i == indexField) continue;
            index = i;
        }

        return fields.get(index);
    }

    private Field getField(int moveCount) {
        return fields.get(moveCount % 2);
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    private void setCurrentMode(Mode currentMode) throws ModeNotFoundException {
        validateCurrentMode(currentMode);
        this.currentMode = currentMode;
    }

    public enum Items {
        LINCORN(4, 1),
        CRUISER(3, 2),
        DESTROYER(2, 3),
        SUBMARINE(1, 4),
        MINE(1, 5);

        private final int size;
        private final int count;

        Items(int size, int count) {
            this.size = size;
            this.count = count;
        }

        public static int getTotalCount() {
            int count = 0;

            for (Items item : Items.values()) {
                count += item.count;
            }

            return count;
        }

        public static Items getItemFromShip(AbstractShip ship) {
            if (ship instanceof Lincorn) {
                return Items.LINCORN;
            } else if (ship instanceof Cruiser) {
                return Items.CRUISER;
            } else if (ship instanceof Destroyer) {
                return Items.DESTROYER;
            } else if (ship instanceof Submarine) {
                return Items.SUBMARINE;
            } else {
                return Items.MINE;
            }
        }

        public static AbstractShip getShip(Items currentShipItem, int[] indexArray) {
            AbstractShip ship;
            switch (currentShipItem) {
                case LINCORN:
                    ship = new Lincorn(indexArray);
                    break;
                case CRUISER:
                    ship = new Cruiser(indexArray);
                    break;
                case DESTROYER:
                    ship = new Destroyer(indexArray);
                    break;
                case SUBMARINE:
                    ship = new Submarine(indexArray);
                    break;
                default:
                    ship = new Mine(indexArray);
                    break;
            }

            return ship;
        }

        public int getSize() {
            return size;
        }

        public int getCount() {
            return count;
        }
    }

    public enum Mode {
        CLASSIC("Classic"),
        SALVO("Salvo"),
        SALVO_BLIND("Salvo Blind");

        private final String modeName;

        Mode(String name) {
            this.modeName = name;
        }

        @Override
        public String toString() {
            return modeName;
        }
    }

}
