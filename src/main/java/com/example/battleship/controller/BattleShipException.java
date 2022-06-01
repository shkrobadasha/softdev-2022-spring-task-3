package com.example.battleship.controller;

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
            super("Player not found");
        }
    }

    public static class ModeNotFoundException extends RegistrationException {
        public ModeNotFoundException() {
            super("Mode not found");
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
            super("Hasn't this ship item");
        }
    }

    public static class ItemSpaceException extends SetupException {

        public ItemSpaceException() {
            super("Hasn't space for shipItem");
        }
    }

    public static class HasUnusedShipException extends SetupException {

        public HasUnusedShipException() {
            super("Has unused shipItems");
        }
    }

}
