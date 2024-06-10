package com.zemlovka.haj.client.fx;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.util.Duration;

public class ToastNotification {
    public static void showToast(VBox owner, String message) {
        Platform.runLater(() -> {
            Popup popup = new Popup();

            Label label = new Label(message);
            label.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 10px;");
            label.setFont(new Font("Arial", 16));

            popup.getContent().add(label);
            popup.setAutoHide(true);

            popup.show(owner.getScene().getWindow());

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(4), label);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setOnFinished(event -> popup.hide());

            fadeTransition.play();
        });
    }
}
