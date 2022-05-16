package com.example.battleship.model;

public class Player {

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override //типа геттер
    public String toString() {
        return "Player " + name;
    }
}

