package com.example.battleship.model;

public abstract class BattleShipException extends Exception{

    public BattleShipException(String message) {
        super(message);
    }

    // Ошибки первой сцены
    public static abstract class RegistrationException extends BattleShipException {

        public RegistrationException(String message) {
            super(message);
        }
    }

    public static class PlayerNotFoundException extends RegistrationException {

        public PlayerNotFoundException() {
            super("Игроки не названы");
        }
    }

    public static class ModeNotFoundException extends RegistrationException {
        public ModeNotFoundException() {
            super("Не выбран режим");
        }
    }

    // Ошибки второй сцены
    public static abstract class SetupException extends BattleShipException {

        public SetupException(String message) {
            super(message);
        }
    }


    public static class ShipItemCountException extends SetupException {

        public ShipItemCountException() {
            super("Капитан, у нас больше нет такого вооружения");
        }
    }

    public static class ItemSpaceException extends SetupException {

        public ItemSpaceException() {
            super("Мало места");
        }
    }


}
