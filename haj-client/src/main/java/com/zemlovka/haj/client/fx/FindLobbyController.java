package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import com.zemlovka.haj.client.ws.WSActions;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Controller class for the Find Lobby screen.
 * This class is responsible for handling user input and rendering the list of lobbies.
 *
 * @author Nikita Korotov
 * @version 1.0
 */
public class FindLobbyController extends AbstractWsActionsSettingController {

    private static final Logger log = LoggerFactory.getLogger(FindLobbyController.class);

    @FXML
    private TextField searchLobbyField;
    @FXML
    private VBox dialogForm;
    @FXML
    private VBox lobbyListView;
    @FXML
    private ScrollPane lobbyListScrollPane;
    @FXML
    private Button backButton;

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
    }

    /**
     * Render the lobby components in the list view.
     *
     * @param lobbyList List of lobbies to render
     */
    private void renderLobbyComponents(List<Lobby> lobbyList) {
        lobbyListView.getChildren().clear();

        for (Lobby lobby : lobbyList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/lobbyComponent.fxml"));
                HBox lobbyComponent = loader.load();

                LobbyComponentController controller = loader.getController();
                controller.setWsActions(wsActions);

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
        try {
            return wsActions.fetchLobbyList().get().lobbies().stream().map(dto -> new Lobby(dto.getName(), null, dto.getCapacity())).toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        //        wsActions.fetchLobbyList().thenApply(f -> {
//            Platform.runLater(() -> {
//                this.lobbyList = LayoutUtil.mapLobbies(f.lobbies(), null);
//                renderLobbyComponents(lobbyList);
//            });
//            return null;
//        }).exceptionally(e -> {
//            log.error("Error fetching lobby list", e);
//            return null;
//        });
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

    /**
     * Function to return to the menu screen. Used by back button.
     */
    @FXML
    private void goMenu() {
        try {
            LayoutUtil.changeLayoutWithFadeTransition((Stage) backButton.getScene().getWindow(), "/com/zemlovka/haj/client/menu.fxml", wsActions);
        } catch (IOException e) {
            log.error("Failed to return to the menu", e);
        }
    }

    @Override
    void setWsActions(WSActions wsActions) {
        super.setWsActions(wsActions);
        lobbyList = createLobbyList();
        renderLobbyComponents(lobbyList);
    }
}