package com.example.battleship.model.ships;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractShip {

    protected int size;
    protected Map<Integer, Boolean> cells = new HashMap<>();

    public AbstractShip(int size, int[] cellIndex) {
        this.size = size;

        for (int i : cellIndex) {
            cells.put(i, false);
        }

    }

    final public boolean isAlive(){
        int count = 0;//читает колиичество убитых клеток корабля

        for (int i = 0; i < cells.size(); i++) {

            if (cells.get(i)) {
                count++;
            }

        }

        return count < cells.size();//если вернет тру,то
    }

    public int getSize() {//геттер для размера
        return size;
    }

    final public boolean hasCells(int index) {
        return cells.containsKey(index);
    }//проверяет,содержится ли корабль данную клеточку

    final public void strike(int index) {
        cells.put(index, true);
    }//метод когда стреляет и попадает в клетку корабля
    // в эту клетку записать тру

}
