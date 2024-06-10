package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.ConnectionStatusListener;
import javafx.application.Platform;
import javafx.stage.Window;

public class ConnectionLostNotifier implements ConnectionStatusListener {
    private final Window owner;
    private boolean showingConnectionLost = false;
    private ToastNotification lostNotification;
    private ToastNotification welcomeBackNotification;

    public ConnectionLostNotifier(Window owner) {
        this.owner = owner;
    }

    @Override
    public void onConnectionLost() {
        if (!showingConnectionLost) {
            showingConnectionLost = true;
            if (welcomeBackNotification != null) {
                welcomeBackNotification.closeToast();
            }
            Platform.runLater(() -> {
                lostNotification = AppState.getInstance().getNotificationService().createToast(owner,
                        "Connection lost, attempting to reconnect...",
                        ToastNotification.Position.CENTER,
                        false,
                        false,
                        true);
                lostNotification.showToast();
            });
        }
    }

    @Override
    public void onConnectionEstablished() {
        if (showingConnectionLost) {
            showingConnectionLost = false;
            if (lostNotification != null) {
                lostNotification.closeToast();
            }
            Platform.runLater(() -> {
                welcomeBackNotification = AppState.getInstance().getNotificationService().createToast(owner,
                        "Welcome back! Connection re-established.",
                        ToastNotification.Position.CENTER,
                        true,
                        false,
                        false);
                welcomeBackNotification.showToast();
            });
        }
    }
}
