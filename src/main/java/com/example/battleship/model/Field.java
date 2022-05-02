package com.example.battleship.model;

import com.example.battleship.model.ships.AbstractShip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Field {//сюда нужно добавить расстановк укораблей(ориентация,вокруг пусто) + мины + выводило новое поле

    private static final int SIZE = 10 * 10;
    private final boolean[] cells;// По умолчанию все false, если в клетку уже стреляли, то true
    private final ArrayList<AbstractShip> ships = new ArrayList<>();//массив всех корабликов

    // первое Int - size корабля, второе - count
    //это мапа для хранения клеточек отдельного корабля
    private final Map<Integer, Integer> validateMap = new HashMap<>();

    public Field() {
        cells = new boolean[SIZE];
    }//тут у нас обьявилось поле размера SIZE 10 на 10
    //заполним поле пустымми клеточками

    public void addShip(AbstractShip ship) {// Просто добавляет корабль в которм уже есть клетки.
        int size = ship.getSize();
        int count = validateMap.get(size);//равно размеру корабля

        validate(size, count);//Проверяет возможность добавление нового корабля данного типа
        validateMap.put(size, count);
        ships.add(ship);
    }

    private void validate(int shipSize, int count) {//проверка чтобы было добавлено только возможное колличество кораблей
        int limit = 4 - shipSize + 1;

        if (limit == count) {
            throw new IllegalArgumentException();
        }
    }

    public boolean strike(int index) {// True - попал, False - мимо
        if (cells[index]) throw new IllegalArgumentException();//если хочет тыкнуть в ту которая уже тру и которая уже помечена на убитую

        cells[index] = true;

        int indexShip = -1;
        boolean isAlive = true;

        for (int i = 0; i < ships.size(); i++) {
            AbstractShip ship = ships.get(i);

            if (ship.hasCells(index)) {
                ship.strike(index);
                indexShip = i;
                isAlive = ship.isAlive();
                break;
            }

        }

        if (indexShip != -1 && !isAlive) {
            ships.remove(index);
        }

        return indexShip != -1;
    }


//проверка что мы добавили все корабли

    public boolean isFinish() {
        return ships.isEmpty();
    }

}
