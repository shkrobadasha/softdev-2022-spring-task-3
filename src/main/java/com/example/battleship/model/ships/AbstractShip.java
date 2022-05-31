package com.example.battleship.model.ships;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractShip {
    protected int size;
    protected Map<Integer, Boolean> cells = new HashMap<>();//мапа для собственных клеток кораблч


    public AbstractShip(int size, int[] cellIndexes) {
        this.size = size;

        for (int i : cellIndexes) {
            cells.put(i, false);
        }

    }

    final public boolean isAlive(){
        int count = 0;//читает колиичество убитых клеток корабля

        for (boolean b : cells.values()) {
            if (b) count++;
        }

        return count < cells.size();
    }

    public int getSize() {//геттер для размера
        return size;
    }

    public ArrayList<Integer> getIndexes(){
        return new ArrayList<>(cells.keySet());
    }
    final public boolean hasCells(int index) {
        return cells.containsKey(index);
    }//проверяет,содержится ли корабль данную клеточку

    final public void strike(int index) {
        cells.put(index, true);
    }//метод когда стреляет и попадает в клетку корабля


}