package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppController {
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    @FXML
    private TextField usernameInputField; // Reference to the TextField in FXML



    @FXML
    private void initialize() {
        System.out.println("Controller initialized");
        log.info("Hello controler started.");
        // Initialize method
        // You can perform any initialization tasks here
    }
    @FXML
    private void onButtonClick(ActionEvent event) {
        String username = usernameInputField.getText(); // Get the text from the TextField
        if (username == null || username.trim().isEmpty()) {
            log.error("Username is empty or null");
            showAlert();
        } else {
            log.info("Username set: {}", username);
            // Proceed with further actions
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Username cannot be empty");
        alert.showAndWait();

        /* //trying to customize the alert, doesn't work

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/com/zemlovka/haj/client/alert.css")).toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
         */
    }
}