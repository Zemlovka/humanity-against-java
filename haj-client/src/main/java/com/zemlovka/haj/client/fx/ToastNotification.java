package com.zemlovka.haj.client.fx;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import java.util.List;

public class ToastNotification {
    private final Popup popup;
    private final Window owner;
    private final String message;
    private final Position position;
    private final boolean autoHide;
    private final boolean canClose;
    private final boolean isError;
    private List<ToastNotification> popups;

    public enum Position {
        CENTER,
        RIGHT_BOTTOM
    }

    public ToastNotification(List<ToastNotification> popups, Popup popup, Window owner, String message, Position position, boolean autoHide, boolean canClose, boolean isError) {
        this.popups = popups;
        this.popup = popup;
        this.owner = owner;
        this.message = message;
        this.position = position;
        this.autoHide = autoHide;
        this.canClose = canClose;
        this.isError = isError;
        createToast();
    }

    public void createToast() {
        Platform.runLater(() -> {
            Label label = new Label(message);
            label.setStyle("-fx-text-fill: white;");
            label.setFont(new Font("Arial", 16));

            HBox notificationBox = new HBox();
            notificationBox.getChildren().add(label);
            if (isError) {
                notificationBox.setStyle("-fx-background-color: rgba(119,17,17,0.5); -fx-background-radius: 10; -fx-padding: 20px;");
            } else {
                notificationBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10; -fx-padding: 20px;");
            }
            notificationBox.setAlignment(Pos.CENTER);
            notificationBox.setSpacing(10);

            if (autoHide) {
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), notificationBox);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.setOnFinished(event -> closeToast());
                popup.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
                    fadeTransition.playFrom(Duration.seconds(0.0));
                    fadeTransition.pause();
                });
                popup.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, event -> fadeTransition.play());
                fadeTransition.play();
            }
            if (canClose) {
                notificationBox.getChildren().add(createCloseButton());
            }

            //dynamic resize
            owner.xProperty().addListener((observable, oldValue, newValue) -> updatePopupPosition(popup));
            owner.yProperty().addListener((observable, oldValue, newValue) -> updatePopupPosition(popup));
            owner.widthProperty().addListener((observable, oldValue, newValue) -> updatePopupPosition(popup));
            owner.heightProperty().addListener((observable, oldValue, newValue) -> updatePopupPosition(popup));

            popup.getContent().add(notificationBox);
            popups.add(this);
            System.out.println("Popups: " + popups.size());

            // Add listener to hide notification when application loses focus
            owner.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) { // !newValue - false if application has lost focus
                    hideToast(false);
                } else {
                    showToast();
                }
            });

        });
//        System.out.println("Popups: " + popups.size());
    }

    public void showToast() {
        Platform.runLater(() -> {
            if (popups.contains(this) && !popup.isShowing()) {
                popup.show(owner);
                updatePopupPosition(popup);

            }
        });
    }

    private void hideToast(boolean animate) {
        Platform.runLater(() -> {
            if (popup.isShowing()) {
                if (animate) {
                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), popup.getContent().get(0));
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);
                    fadeOut.setOnFinished(e -> popup.hide());
                    fadeOut.play();
                } else {
                    popup.hide();

                }
            }
        });
    }

    public void closeToast() {
        hideToast(true);
        popups.remove(this);
        popups.forEach(toastNotification -> {
            if (this.getPosition() == Position.RIGHT_BOTTOM) {
                toastNotification.updatePopupPosition(toastNotification.popup);
            }
        });
    }

    private Button createCloseButton() {
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-radius: 50%; -fx-font-size: 12;");
        closeButton.setOnAction(event -> closeToast());
        return closeButton;
    }

    private void updatePopupPosition(Popup popup) {
        double x = 0;
        double y = 0;
        if (position == Position.RIGHT_BOTTOM) {
            double gap = 10;
            x = owner.getX() + owner.getWidth() - popup.getWidth() - 40;
            int index = popups.indexOf(this);
            y = owner.getY() + owner.getHeight() - popup.getHeight() - 40 - (popup.getHeight() + gap) * index;

            //System.out.println(popup.getY());
            if (popup.getY()>owner.getHeight() - popup.getHeight() - 25) {
                popup.hide();
            } else {
                popup.show(owner);
            }
            popup.setX(x);
            popup.setY(y);
        }
        if (position == Position.CENTER) {
            x = owner.getX() + owner.getWidth() / 2 - popup.getWidth() / 2;
            y = owner.getY() + 80;
            if (popup.getY()>owner.getHeight() - popup.getHeight() - 25) {
                popup.hide();
            } else {
                popup.show(owner);
            }
            popup.setX(x);
            popup.setY(y);
        }
    }

    public Position getPosition() {
        return position;
    }
}
