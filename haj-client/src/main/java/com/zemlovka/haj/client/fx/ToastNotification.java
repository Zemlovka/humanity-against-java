package com.zemlovka.haj.client.fx;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.util.Duration;

public class ToastNotification {
    public enum Position {
        CENTER,
        RIGHT_BOTTOM
    }

    public static void showToast(VBox owner, String message) {
        showToast(owner, message, Position.CENTER, true, false);
    }

    public static void showToast(VBox owner, String message, Position position, boolean autoHide, boolean canClose) {
        Platform.runLater(() -> {
            Popup popup = new Popup();
            double x = 0;
            double y = 0;

            Label label = new Label(message);
            label.setStyle("-fx-text-fill: white;");
            label.setFont(new Font("Arial", 16));

            HBox notificationBox = new HBox();
            notificationBox.getChildren().add(label);
            notificationBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10; -fx-padding: 20px;");
            notificationBox.setAlignment(Pos.CENTER);
            notificationBox.setSpacing(10);

            if (autoHide) {
                //popup.setAutoHide(true);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(4), notificationBox);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.setOnFinished(event -> popup.hide());
                fadeTransition.play();
            }
            if (canClose) {
                notificationBox.getChildren().add(createCloseButton(popup));
            }
            if (position == Position.RIGHT_BOTTOM) {
                y = owner.getScene().getWindow().getHeight() - 100;
                x = owner.getScene().getWindow().getWidth() - 60;
            }
            if (position == Position.CENTER) {
                y = owner.getScene().getWindow().getY() + 80;
                x = owner.getScene().getWindow().getWidth() / 2;
            }
            popup.getContent().add(notificationBox);
            popup.setX(x);
            popup.setY(y);
            popup.show(owner.getScene().getWindow());
        });
    }

    private static Button createCloseButton(Popup popup) {
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-radius: 50%; -fx-font-size: 12;");
        closeButton.setOnAction(event -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), popup.getContent().get(0));
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> popup.hide());
            fadeOut.play();
        });
        return closeButton;
    }
}