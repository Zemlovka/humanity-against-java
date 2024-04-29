package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Client;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class FindLobbyController {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    @FXML
    private TextField usernameInputField;
    @FXML
    private VBox dialogForm;

    @FXML
    private void initialize() {
        log.info("Find Lobby controller started.");

        dialogForm.setOpacity(0); // Set the initial opacity to 0 (fully transparent), for animation purposes

    }
}