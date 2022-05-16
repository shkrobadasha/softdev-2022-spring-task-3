package com.example.battleship.controller;

import com.example.battleship.model.BattleShipException;
import com.example.battleship.view.NotificationUtil;

public class BattleShipExceptionHandler {

    public static void handle(BattleShipException exception) {
        NotificationUtil.notifyError(exception.getMessage());
    }

}
