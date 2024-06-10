package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import com.zemlovka.haj.client.ws.WSActions;
import com.zemlovka.haj.client.ws.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

public class CreateLobbyController extends AbstractWsActionsSettingController {
    @FXML
    private VBox dialogForm;
    @FXML
    private TextField lobbyNameField;
    @FXML
    private TextField lobbyPasswordField;
    @FXML
    private Slider lobbySlider;
    @FXML
    private Button backButton;

    private WSActions WSActions;

    private final AppState appState = AppState.getInstance();
    private static final Logger log = LoggerFactory.getLogger(CreateLobbyController.class);

    @FXML
    private void initialize() {
        log.info("CreateLobby controller init.");
        lobbyNameField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!event.isAltDown()) {
                    onCreateLobbyClick(new ActionEvent());
                    event.consume();

                }
            }
        });
        lobbyPasswordField.setDisable(true);
    }

    @FXML
    private void onCreateLobbyClick(ActionEvent event) {
        String lobbyName = lobbyNameField.getText();

        String lobbyPassword = lobbyPasswordField.getText();
        int lobbySize = (int) lobbySlider.getValue();
        log.info("Lobby name: {}", lobbyName);
        log.info("Lobby password: {}", lobbyPassword);
        log.info("Lobby size: {}", lobbySize);
        if (lobbyName == null || lobbyName.trim().isEmpty()) {
            log.error("Lobby name is empty or null");
            ToastNotification tn = appState.getNotificationService().createToast(dialogForm.getScene().getWindow(),
                    "Lobby name cannot be empty",
                    ToastNotification.Position.RIGHT_BOTTOM,
                    false,
                    true,
                    true);
            tn.showToast();
        } else {
            Lobby lobby = new Lobby(lobbyName, lobbyPassword, lobbySize);
            WSActions.createLobby(lobby);
            Player player = appState.getCurrentPlayer();
            lobby.addPlayerToList(player);

            appState.setCurrentLobby(lobby);
            log.info("Creating lobby: {}", lobbyName);
            try {
                LayoutUtil.changeLayoutWithFadeTransition((Stage) lobbyNameField.getScene().getWindow(), "/com/zemlovka/haj/client/lobby.fxml", WSActions);
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Function to return to the menu screen. Used by back button.
     */
    @FXML
    private void goMenu() {
        try {
            LayoutUtil.changeLayoutWithFadeTransition((Stage) backButton.getScene().getWindow(), "/com/zemlovka/haj/client/menu.fxml", WSActions);
        } catch (IOException e) {
            log.error("Failed to return to the menu", e);
        }
    }

    /**
     * Sets the {@link WSActions} object to be used by the controller.
     *
     * @param wsActions The WSActions object
     */
    public void setWsActions(WSActions wsActions) {
        this.WSActions = wsActions;
    }
}