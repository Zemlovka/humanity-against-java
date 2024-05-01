package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.LobbyClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FindLobbyController {

    private static final Logger log = LoggerFactory.getLogger(LobbyClient.class);

    @FXML
    private TextField usernameInputField;
    @FXML
    private VBox dialogForm;

    @FXML
    private void initialize() {
        log.info("Find Lobby controller started.");

        LayoutUtil.fadeInTransition(dialogForm);

    }
}