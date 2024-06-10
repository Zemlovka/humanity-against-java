package com.zemlovka.haj.client.fx;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;

public class ToastNotification {
    private final Popup popup;
    private final Window owner;
    private final String message;
    private final Position position;
    private final boolean autoHide;
    private final boolean canClose;
    private final boolean isError; // New field to indicate if it's an error popup

    public enum Position {
        CENTER,
        RIGHT_BOTTOM
    }
    public ToastNotification(Window owner, String message, Position position, boolean autoHide, boolean canClose) {
        this.popup = new Popup();
        this.owner = owner;
        this.message = message;
        this.position = position;
        this.autoHide = autoHide;
        this.canClose = canClose;
        this.isError = false;
    }

    public ToastNotification(Window owner, String message, Position position, boolean autoHide, boolean canClose, boolean isError) {
        this.popup = new Popup();
        this.owner = owner;
        this.message = message;
        this.position = position;
        this.autoHide = autoHide;
        this.canClose = canClose;
        this.isError = isError;
    }

    public void showToast() {
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
                fadeTransition.setOnFinished(event -> popup.hide());
                fadeTransition.play();
            }
            if (canClose) {
                notificationBox.getChildren().add(createCloseButton());
            }

            popup.getContent().add(notificationBox);
            popup.show(owner);
            updatePopupPosition();

            // Add listener to update position when stage is resized or move
            owner.xProperty().addListener((observable, oldValue, newValue) -> updatePopupPosition());
            owner.yProperty().addListener((observable, oldValue, newValue) -> updatePopupPosition());
            owner.widthProperty().addListener((observable, oldValue, newValue) -> updatePopupPosition());
            owner.heightProperty().addListener((observable, oldValue, newValue) -> updatePopupPosition());
        });
    }

    public void closeToast() {
        Platform.runLater(() -> {
            if (popup.isShowing()) {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), popup.getContent().get(0));
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(e -> popup.hide());
                fadeOut.play();
            }
        });
    }

    private Button createCloseButton() {
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-radius: 50%; -fx-font-size: 12;");
        closeButton.setOnAction(event -> closeToast());
        return closeButton;
    }

    private void updatePopupPosition() {
        double x = 0;
        double y = 0;

        if (position == Position.RIGHT_BOTTOM) {
            x = owner.getX() + owner.getWidth() - this.popup.getWidth() - 40;
            y = owner.getY() + owner.getHeight() - this.popup.getHeight() - 40;
        }
        if (position == Position.CENTER) {
            x = owner.getX() + owner.getWidth() / 2 - this.popup.getWidth() / 2;
            y = owner.getY() + 80;
        }
        popup.setX(x);
        popup.setY(y);
    }
}
