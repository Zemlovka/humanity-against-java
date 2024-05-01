package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.LobbyClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller for the main menu of the game. Handles the main menu buttons.
 * <p>
 *
 * @author Nikita Korotov
 * @version 1.0
 */
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @FXML
    private VBox dialogForm;
    @FXML
    private Button findLobbyButton;
    @FXML
    private Button createLobbyButton;
    @FXML
    private Button aboutGameButton;


    @FXML
    private void initialize() {
        log.info("Menu controler started.");
        LayoutUtil.fadeInTransition(dialogForm);

    }

    /**
     * Handles the button clicks in the main menu. Changes the layout to the corresponding one, which are findLobby, createLobby and aboutGame.
     *
     * @param event the event that triggered the method
     */
    @FXML
    private void onButtonClick(ActionEvent event) {
        if (event.getSource() == findLobbyButton) {
            try {
                LayoutUtil.changeLayoutWithFadeTransition((Stage) findLobbyButton.getScene().getWindow(), "/com/zemlovka/haj/client/findLobby.fxml");
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
        } else if (event.getSource() == createLobbyButton) {
            try {
                LayoutUtil.changeLayoutWithFadeTransition((Stage) findLobbyButton.getScene().getWindow(), "/com/zemlovka/haj/client/createLobby.fxml");
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
        } else if (event.getSource() == aboutGameButton) {
            log.info("About game button clicked");
            try {
                LayoutUtil.changeLayoutWithFadeTransition((Stage) findLobbyButton.getScene().getWindow(), "/com/zemlovka/haj/client/findLobby.fxml");
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
        }
    }
}