package com.example.battleship.model;

import com.example.battleship.model.ships.AbstractShip;
import com.example.battleship.model.ships.implementation.Mine;

import java.util.*;

public class Field {

    private static final int SIZE = 10 * 10;
    private final boolean[] cells;
    private final List<AbstractShip> ships = new ArrayList<>();//массив всех корабликов

    private FieldAction fieldActionListener;
    private StrikeAction strikeActionListener;

    // первое Int - size корабля, второе - count
    //для хранения всех кораблей
    private final Map<Integer, Integer> validateMap = new HashMap<>();

    public Field() {
        cells = new boolean[SIZE];
    }//тут у нас объявилось поле размера SIZE 10 на 10
    //заполним поле пустыми клеточками

    public void setActionListener(FieldAction fieldActionListener) {
        this.fieldActionListener = fieldActionListener;
    }

    public void setStrikeActionListener(StrikeAction strikeActionListener) {
        this.strikeActionListener = strikeActionListener;
    }

    public void addShip(AbstractShip ship) {
        int size = ship.getSize();
        int count = validateMap.getOrDefault(size, 0);

        if (ship instanceof Mine) {
            size = 0; // Для того чтобы в validate макс кол-во было 5
        }

        validate(size, count);//Проверяет возможность добавление нового корабля данного типа(размера)
        count++;
        validateMap.put(size, count);
        ships.add(ship);
    }

    private void validate(int shipSize, int count) {//проверка чтобы было добавлено только возможное количество кораблей
        int limit = 4 - shipSize + 1;

        if (limit == count) {
            throw new IllegalArgumentException();
        }
    }

    /*public void strike(int index) {
        strike(index, true);
    }*/

    public StrikeAction.Result strike(int index, boolean isNeedChangeTurn) {
        if (cells[index]) {
            return StrikeAction.Result.EMPTY; // Может возникнуть ситуация, когда игрок попал в мину, а мина второго игрока попала мину первого
        }

        cells[index] = true;

        for (int indexShip = 0; indexShip < ships.size(); indexShip++) {
            AbstractShip ship = ships.get(indexShip);

            if (ship.hasCells(index)) {
                ship.strike(index);

                boolean isMine = ship instanceof Mine;

                StrikeAction.Result result;
                if (isMine) {
                    result = StrikeAction.Result.MINE;
                    strikeAction(index, StrikeAction.Result.MINE);
                } else {
                    result = StrikeAction.Result.SHIP;
                    strikeAction(index, StrikeAction.Result.SHIP);
                }

                if (!ship.isAlive()) {

                    if (isMine) {
                        mineWasSunkAction(index);
                        changeTurnIfIsNeeded(isNeedChangeTurn);
                    } else {
                        shipWasSunkAction();
                    }

                    removeShip(indexShip);
                }

                return result;
            }
        }

        strikeAction(index, StrikeAction.Result.MISS);
        changeTurnIfIsNeeded(isNeedChangeTurn);

        return StrikeAction.Result.MISS;
    }

    private void changeTurnIfIsNeeded(boolean isNeed) {
        if (isNeed) {
            changeTurnAction();
        }
    }

    private void removeShip(int indexShip) {
        ships.remove(indexShip);
        if (isFinish()) finishGameAction();
    }

    private void finishGameAction() {
        if (fieldActionListener != null) {
            fieldActionListener.finishGame(this);
        }
    }

    private void shipWasSunkAction() {
        if (fieldActionListener != null) {
            fieldActionListener.shipWasSunk(this);
        }
    }

    private void mineWasSunkAction(int index) {
        if (fieldActionListener != null) {
            fieldActionListener.mineWasSunk(this, index);
        }
    }

    private void changeTurnAction() {
        if (fieldActionListener != null) {
            fieldActionListener.changeTurn(this);
        }
    }

    private void strikeAction(int index, StrikeAction.Result result) {
        if (strikeActionListener != null) {
            strikeActionListener.onStrike(index, result);
        }
    }

    public boolean isFinish() {
        for (AbstractShip ship : ships) {
            if (ship instanceof Mine) continue;

            return false;
        }

        return true;
    }

    public int getMaxSizeOfAliveShip() {
        int max = 0;

        for (AbstractShip ship : ships) {
            max = Math.max(max, ship.getSize());
        }

        return max;
    }

    public void setEnable(boolean isEnable) {
        if (strikeActionListener != null) {
            strikeActionListener.setEnable(isEnable);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;

        return Arrays.equals(cells, field.cells) && Objects.equals(ships, field.ships)
                && Objects.equals(fieldActionListener, field.fieldActionListener)
                && Objects.equals(validateMap, field.validateMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ships, fieldActionListener, validateMap, Arrays.hashCode(cells));
    }
    public interface FieldAction {

        void changeTurn(Field field);

        void finishGame(Field field);

        void shipWasSunk(Field field);

        void mineWasSunk(Field field, int index);

    }

    public interface StrikeAction {

        enum Result {SHIP, MINE, MISS, EMPTY}

        void onStrike(int index, Result result);

        void setEnable(boolean isEnable);

    }

}
