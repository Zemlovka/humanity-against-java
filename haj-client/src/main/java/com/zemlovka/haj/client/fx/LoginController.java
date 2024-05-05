package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.LobbyWSActions;
import com.zemlovka.haj.client.ws.Player;
import com.zemlovka.haj.utils.dto.server.UserConnectionResponseDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Controller for the login screen. Handles the login process (just a simple username input for now).
 * <p>
 * Uses the {@link LobbyWSActions} to connect to the server and send the username.
 *
 * @author Nikita Korotov
 * @version 1.0
 */

public class LoginController extends AbstractWsActionsSettingController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final AppState appState= AppState.getInstance();
    @FXML
    private TextField usernameInputField;
    @FXML
    private VBox dialogForm;

    @FXML
    private void initialize() {
        log.info("Login controller started.");
        // Event listener for the Enter key press event
        usernameInputField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onButtonClick(new ActionEvent());
                event.consume(); // Prevent further handling of the Enter key press event
            }
        });
    }

    /**
     * Event handler for the button click event. Gets the username from the input field and sends it to the server.
     *
     * @param event The event object
     */
    @FXML
    private void onButtonClick(ActionEvent event) {
        String username = usernameInputField.getText(); // Get the text from the TextField
        if (username == null || username.trim().isEmpty()) {
            log.error("Username is empty or null");
            LayoutUtil.showAlert(Alert.AlertType.ERROR, "Error", "Username cannot be empty");
        } else {
            Player player = new Player(username, "1", true);
            try {
                //todo
                UserConnectionResponseDTO userConnectionResponseDTO = wsActions.login(username).get();
                if (userConnectionResponseDTO.isSuccesful())
                    log.info("Username set: {}", username);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            appState.setCurrentPlayer(player);
            // Proceed with further actions
            try {
                //changeLayout();
                LayoutUtil.changeLayoutWithFadeTransition((Stage) usernameInputField.getScene().getWindow(), "/com/zemlovka/haj/client/menu.fxml", wsActions);
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Event handler for the TAC button click event. Opens the Terms and Conditions window.
     *
     * @param event The event object
     * @throws IOException If the FXML file is not found
     */
    @FXML
    private void openTAC(ActionEvent event) throws IOException {
        Stage tacStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/tac.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        tacStage.setScene(scene);
        tacStage.show();
    }

}