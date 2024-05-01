package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
        // Initialize the controller
        // For example, set the button text and image view properties
        //joinLobbyButton.setText("Join Lobby");
        //lobbyImageView.setImage(new Image("/path/to/image"));
    }

    public void setLobby(Lobby lobby) {
        // Set the lobby title
        lobbyTitle.setText(lobby.getName());

        String lobbySizeString = "1/"+lobby.getSize();
        lobbySize.setText(lobbySizeString);

        // Set the lobby size label
        //lobbySize.setText(lobby.getSize());

        // Set other properties as needed
        // For example, set the image view and button properties
        //lobbyImageView.setImage(lobbyData.getImage());
        //joinLobbyButton.setText(lobbyData.getButtonText());
    }
}
