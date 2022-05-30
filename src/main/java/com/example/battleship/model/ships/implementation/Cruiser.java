package com.example.battleship.model.ships.implementation;


import com.example.battleship.model.ships.AbstractShip;

public class Cruiser extends AbstractShip {

    public Cruiser(int[] cellIndexes) {//для каждого отдельного корабля обьявляется массив
        super(3, cellIndexes);
    }

}
