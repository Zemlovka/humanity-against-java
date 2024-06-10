package com.zemlovka.haj.client.fx;

import javafx.stage.Popup;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private List<ToastNotification> popups;
    public NotificationService() {
        popups = new ArrayList<>();
    }
    public ToastNotification createToast(Window owner, String message, ToastNotification.Position position, boolean autoHide, boolean canClose, boolean isError) {
        ToastNotification notification = new ToastNotification(popups, new Popup(), owner, message, position, autoHide, canClose, isError);
        System.out.println(popups);
        return notification;
    }
}
