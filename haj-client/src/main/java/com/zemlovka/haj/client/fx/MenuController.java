package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Client;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    @FXML
    private VBox dialogForm;

    @FXML
    private void initialize() {
        log.info("Menu controler started.");
        // Initialize method
        dialogForm.setOpacity(0); // Set the initial opacity to 0 (fully transparent)
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), dialogForm);
        fadeTransition.setFromValue(0); // Start with opacity 0 (fully transparent)
        fadeTransition.setToValue(1); // Fade to opacity 1 (fully opaque)
        fadeTransition.play(); // Start the fade-in animation
        // You can perform any initialization tasks here
    }

}