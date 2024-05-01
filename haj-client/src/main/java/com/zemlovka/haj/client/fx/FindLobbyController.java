package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import javafx.event.Event;
import com.zemlovka.haj.client.ws.LobbyClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the Find Lobby screen.
 * This class is responsible for handling user input and rendering the list of lobbies.
 *
 * @author Nikita Korotov
 * @version 1.0
 */
public class FindLobbyController {

    private static final Logger log = LoggerFactory.getLogger(FindLobbyController.class);

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
        We really need these two lines. Bug caused by the requestLayout() method which is set/called via ScrollPaneBehavior
        More: https://stackoverflow.com/questions/53603250/javafx-how-to-prevent-label-text-resize-during-scrollpane-focus
        */
        lobbyListScrollPane.setOnMousePressed(Event::consume);
        lobbyListView.setOnMousePressed(Event::consume);

        lobbyList = createLobbyList();
        renderLobbyComponents(createLobbyList());

    }

    /**
     * Render the lobby components in the list view.
     *
     * @param lobbyList List of lobbies to render
     */
    private void renderLobbyComponents(List<Lobby> lobbyList) {
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

    /**
     * Create a list of lobbies to display.
     *
     * @return List of lobbies
     */
    private List<Lobby> createLobbyList() {
        return List.of(new Lobby("Misha loh", "Label 1", 4), new Lobby("test", "Label 2", 6), new Lobby("Go lol", "Label 3", 1), new Lobby("Ya ustal", "Label 4", 3), new Lobby("pls help", "Label 5", 2));
    }

    /**
     * Search for lobbies based on the input from the search field
     */
    @FXML
    private void searchLobbies() {
        String searchTerm = searchLobbyField.getText().trim().toLowerCase();
        List<Lobby> filteredLobbies = filterLobbies(lobbyList, searchTerm);

        renderLobbyComponents(filteredLobbies);
    }

    /**
     * Filters the list of lobbies based on the search string
     *
     * @param lobbies    List of lobbies to filter
     * @param searchTerm Search term to filter by
     * @return List of lobbies that match the search term
     */
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