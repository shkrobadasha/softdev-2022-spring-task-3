package com.example.battleship.controller;

import com.example.battleship.model.BattleShipException;
import com.example.battleship.model.BattleShipException.ModeNotFoundException;
import com.example.battleship.model.BattleShipException.PlayerNotFoundException;
import com.example.battleship.model.Field;
import com.example.battleship.model.Player;

public class Game {


    private Player[] players;
    private Field[] fields;
    private Mode currentMode;

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

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    private void setCurrentMode(Mode currentMode) throws ModeNotFoundException {
        validateCurrentMode(currentMode);
        this.currentMode = currentMode;
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

    // TODO:
    //  2) 2 поля,которые будут меняться
    //  3) при расстановке кораблей смотреть чтобы нельзя было поставить вокруг них,ориентация корабля
    //  3) делаю логику игры,потом думаю над режимами
    //  3) GUI
    //  4) State (Регистрация, Процесс, Финиш)

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

    public void clear() {
        players = null;
        fields = null;
        currentMode = null;
    }


}
