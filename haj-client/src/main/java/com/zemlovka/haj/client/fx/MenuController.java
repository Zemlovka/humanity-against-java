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


public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(LobbyClient.class);

    @FXML
    private VBox dialogForm;
    @FXML
    private Button findLobbyButton;
    @FXML
    private Button hostLobbyButton;
    @FXML
    private Button aboutGameButton;


    @FXML
    private void initialize() {
        log.info("Menu controler started.");
        LayoutUtil.fadeInTransition(dialogForm);

    }

    @FXML
    private void onButtonClick(ActionEvent event) {
        if (event.getSource() == findLobbyButton) {
            log.info("Find lobby button clicked");
            try {
                LayoutUtil.changeLayoutWithFadeTransition((Stage) findLobbyButton.getScene().getWindow(), "/com/zemlovka/haj/client/findLobby.fxml");
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
        } else if (event.getSource() == hostLobbyButton) {
            log.info("Host lobby button clicked");
            try {
                LayoutUtil.changeLayoutWithFadeTransition((Stage) findLobbyButton.getScene().getWindow(), "/com/zemlovka/haj/client/findLobby.fxml");
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