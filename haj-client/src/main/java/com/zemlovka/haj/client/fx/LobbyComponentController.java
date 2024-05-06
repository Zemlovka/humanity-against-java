package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller for the lobby component, which is a single lobby in the lobby list
 *
 * @author Korotov Nikita
 * @version 1.0
 * @see Lobby (entity)
 * @see FindLobbyController (parent controller)
 */
public class LobbyComponentController extends AbstractWsActionsSettingController {
    private static final Logger log = LoggerFactory.getLogger(LobbyComponentController.class);
    private final AppState appState = AppState.getInstance();
    private Lobby lobby;

    @FXML
    private HBox lobbyComponent;

    @FXML
    private Label lobbyTitle;

    @FXML
    private Label lobbySize;

    @FXML
    private ImageView lobbyImageView;

    @FXML
    private Button joinLobbyButton;

    @FXML
    private void initialize(Lobby lobby) {
        log.info("Lobby component initialized: {} ", lobby.getName());
    }

    /**
     * Set the lobby data for the component
     *
     * @param lobby The lobby entity to take data from
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
        // Set the lobby title
        lobbyTitle.setText(lobby.getName());

        String lobbySizeString = "1/" + lobby.getSize();
        lobbySize.setText(lobbySizeString);

    }

    @FXML
    private void onJoinLobbyClick() {
        log.info("Join lobby button clicked");
        try {
            appState.setCurrentLobby(lobby);
            LayoutUtil.changeLayoutWithFadeTransition((Stage) joinLobbyButton.getScene().getWindow(), "/com/zemlovka/haj/client/lobby.fxml", wsActions);
        } catch (IOException e) {
            log.error("Error changing layout: ", e);
        }
    }
}
