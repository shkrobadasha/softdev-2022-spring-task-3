package com.example.battleship.view;

import org.controlsfx.control.Notifications;

public class NotificationUtil {

    enum Type {INFORM, ERROR}

    public static void notify(Type type, String title, String message) {
        Notifications notifications = Notifications.create().title(title).text(message);

        switch (type) {
            case INFORM:
                notifications.showInformation();
                break;
            case ERROR:
                notifications.showError();
                break;
        }
    }

    public static void notifyError(String message) {
        notify(Type.ERROR, Type.ERROR.name(), message);
    }

    public static void notifyInfo(String playerName, String message) {
        notify(Type.INFORM, playerName, message);
    }

}
