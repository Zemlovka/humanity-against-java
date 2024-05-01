package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Controller for the lobby component, which is a single lobby in the lobby list
 *
 * @author Korotov Nikita
 * @version 1.0
 * @see Lobby (entity)
 * @see FindLobbyController (parent controller)
 */
public class LobbyComponentController {

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

    }

    /**
     * Set the lobby data for the component
     *
     * @param lobby The lobby entity to take data from
     */
    public void setLobby(Lobby lobby) {
        // Set the lobby title
        lobbyTitle.setText(lobby.getName());

        String lobbySizeString = "1/" + lobby.getSize();
        lobbySize.setText(lobbySizeString);

        // Set the lobby size label
        //lobbySize.setText(lobby.getSize());
    }
}
