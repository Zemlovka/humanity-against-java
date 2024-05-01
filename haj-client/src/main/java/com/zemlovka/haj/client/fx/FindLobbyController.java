package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Client;
import com.zemlovka.haj.client.ws.Lobby;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FindLobbyController {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    @FXML
    private TextField searchLobbyField;
    @FXML
    private VBox dialogForm;
    @FXML
    private VBox lobbyListView;
    @FXML
    private ScrollPane lobbyListScrollPane;

    List<Lobby> lobbyList = new ArrayList<>();

    @FXML
    private void initialize() {
        log.info("Find Lobby controller started.");
        LayoutUtil.fadeInTransition(dialogForm);
        /*
        We really need 2 bottom lines. Bug caused by the requestLayout() method which is set/called via ScrollPaneBehavior
        More: https://stackoverflow.com/questions/53603250/javafx-how-to-prevent-label-text-resize-during-scrollpane-focus
        */
        lobbyListScrollPane.setOnMousePressed(Event::consume);
        lobbyListView.setOnMousePressed(Event::consume);

        lobbyList = createLobbyList();
        renderLobbyComponents(createLobbyList());

    }

    private void renderLobbyComponents(List<Lobby>lobbyList) {
        lobbyListView.getChildren().clear();

        // Loop through the lobby data list and add lobby components to the VBox
        for (Lobby lobby : lobbyList) {
            try {
                // Load the lobby component FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/lobbyComponent.fxml"));
                HBox lobbyComponent = loader.load();

                LobbyComponentController controller = loader.getController();

                controller.setLobby(lobby);

                lobbyListView.getChildren().add(lobbyComponent);
            } catch (IOException e) {
                log.error("Error loading lobby component", e);
            }
        }
    }

    private List<Lobby> createLobbyList() {
        return List.of(
                new Lobby("Misha loh", "Label 1", 4),
                new Lobby("test", "Label 2", 6),
                new Lobby("Go lol", "Label 3", 1),
                new Lobby("Ya ustal", "Label 4", 3),
                new Lobby("pls help", "Label 5", 2));
    }
    @FXML
    private void searchLobbies() {
        String searchTerm = searchLobbyField.getText().trim().toLowerCase();
        List<Lobby> filteredLobbies = filterLobbies(lobbyList, searchTerm);

        renderLobbyComponents(filteredLobbies);
    }

    private List<Lobby> filterLobbies(List<Lobby> lobbies, String searchTerm) {
        List<Lobby> filteredLobbies = new ArrayList<>();
        for (Lobby lobby : lobbies) {
            if (lobby.getName().toLowerCase().contains(searchTerm)) {
                filteredLobbies.add(lobby);
            }
        }
        return filteredLobbies;
    }
}