package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class AppController {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    @FXML
    private TextField usernameInputField; // Reference to the TextField in FXML

    @FXML
    private void initialize() {
        log.info("App controler started.");
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
            try {
                changeLayout();
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Username cannot be empty");
        alert.showAndWait();

        /*  //trying to customize the alert, doesn't work
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/com/zemlovka/haj/client/alert.css")).toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
         */ //trying to customize the alert, doesn't work
    }

    public void changeLayout() throws IOException {
            //creating menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameInputField.getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/com/zemlovka/haj/client/styles.css").toExternalForm());
            stage.setScene(scene);
    }

}