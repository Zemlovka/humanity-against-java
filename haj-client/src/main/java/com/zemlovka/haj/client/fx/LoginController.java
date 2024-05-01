package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.client.ws.LobbyWSActions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LobbyClient.class);

    private LobbyWSActions wsActions;
    @FXML
    private TextField usernameInputField;
    @FXML
    private VBox dialogForm;

    @FXML
    private void initialize() {
        log.info("Login controller started.");
    }

    @FXML
    private void onButtonClick(ActionEvent event) {
        String username = usernameInputField.getText(); // Get the text from the TextField
        if (username == null || username.trim().isEmpty()) {
            log.error("Username is empty or null");
            LayoutUtil.showAlert(Alert.AlertType.ERROR, "Error", "Username cannot be empty");
        } else {
            log.info("Username set: {}", username);
            wsActions.connect(username);
            // Proceed with further actions
            try {
                //changeLayout();
                LayoutUtil.changeLayoutWithFadeTransition((Stage) usernameInputField.getScene().getWindow(), "/com/zemlovka/haj/client/menu.fxml");
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void openTAC(ActionEvent event) throws IOException {
        Stage tacStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/tac.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        tacStage.setScene(scene);
        tacStage.show();
    }

    public void setWsActions(LobbyWSActions wsActions) {
        this.wsActions = wsActions;
    }
}