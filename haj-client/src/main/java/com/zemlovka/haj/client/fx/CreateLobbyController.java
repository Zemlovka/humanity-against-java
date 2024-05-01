package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller for the Create Lobby screen. Handles the creation of a new lobby.
 * <p>
 *
 * @author Nikita Korotov
 * @version 1.0
 */

public class CreateLobbyController {
    @FXML
    private VBox dialogForm;
    @FXML
    private TextField lobbyNameField;
    @FXML
    private TextField lobbyPasswordField;
    @FXML
    private Slider lobbySlider;


    private static final Logger log = LoggerFactory.getLogger(CreateLobbyController.class);

    @FXML
    private void initialize() {
        log.info("CreateLobby controller started.");
    }

    @FXML
    private void onCreateLobbyClick(ActionEvent event) {
        String lobbyName = lobbyNameField.getText(); // Get the text from the TextField
        String lobbyPassword = lobbyPasswordField.getText();
        int lobbySize = (int) lobbySlider.getValue();
        log.info("Lobby name: {}", lobbyName);
        log.info("Lobby password: {}", lobbyPassword);
        log.info("Lobby size: {}", lobbySize);
        if (lobbyName == null || lobbyName.trim().isEmpty()) {
            log.error("Lobby name is empty or null");
            LayoutUtil.showAlert(Alert.AlertType.ERROR, "Error", "Lobby name cannot be empty");
        } else {
            Lobby lobby = new Lobby(lobbyName, lobbyPassword, lobbySize);
            log.info("Creating lobby: {}", lobbyName, lobbySize);
            // Proceed with further actions
            /*
            try {
                LayoutUtil.changeLayoutWithFadeTransition((Stage) lobbyNameField.getScene().getWindow(), "/com/zemlovka/haj/client/menu.fxml");
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
            */
        }
    }
}