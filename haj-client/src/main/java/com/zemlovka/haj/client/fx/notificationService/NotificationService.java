package com.zemlovka.haj.client.fx.notificationService;

import javafx.stage.Popup;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for creating notifications. Contains a list of all currently active notifications.
 * <p>
 *
 * @author Nikita Korotov
 * @version 1.0
 */
public class NotificationService {
    private final List<ToastNotification> popups;

    public NotificationService() {
        popups = new ArrayList<>();
    }

    /**
     * Creates a new toast notification and adds it to the list of active notifications.
     * If there are more than 7 notifications, the oldest one will be removed.
     * @param owner window to show the notification on
     * @param message message to display
     * @param position position of the notification
     * @param autoHide should the notification hide automatically
     * @param canClose can the notification be closed
     * @param isError is the notification an error
     * @return the created notification
     */
    public ToastNotification createToast(Window owner, String message, ToastNotification.Position position, boolean autoHide, boolean canClose, boolean isError) {
        if (popups.size() > 7) { //no more than 7 popups at the time
            popups.remove(0);
        }
        return new ToastNotification(popups, new Popup(), owner, message, position, autoHide, canClose, isError);
    }

}
