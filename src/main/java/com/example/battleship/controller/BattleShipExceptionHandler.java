package com.example.battleship.controller;

import com.example.battleship.view.NotificationUtil;

public class BattleShipExceptionHandler {

    public static void handle(BattleShipException exception) {
        exception.printStackTrace();//для печати ошибок в логах

        String message = "Упс, что-то пошло не так";

        if (exception instanceof BattleShipException.PlayerNotFoundException) {
            message ="Игроки не названы";
        } else if (exception instanceof BattleShipException.ModeNotFoundException) {
            message = "Не выбран режим";
        } else if (exception instanceof BattleShipException.ShipItemCountException) {
            message = "Капитан, у нас больше нет такого вооружения";
        } else if (exception instanceof BattleShipException.ItemSpaceException) {
            message = "Мало места";
        } else if (exception instanceof BattleShipException.HasUnusedShipException) {
            message = "Не всё вооружение задействовано, капитан";
        }

        NotificationUtil.notifyError(message);
    }

}
