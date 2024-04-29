package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Client;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    @FXML
    private TextField usernameInputField; // Reference to the TextField in FXML
    @FXML
    private VBox dialogForm;

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
                //changeLayout();
                LayoutUtil.changeLayoutWithFadeTransition((Stage) usernameInputField.getScene().getWindow(), "/com/zemlovka/haj/client/menu.fxml");
            } catch (IOException e) {
                log.error("Failed to change layout", e);
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    private void openTAC(ActionEvent event) throws IOException{
        Stage tacStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/tac.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        tacStage.setScene(scene);
        tacStage.show();
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
        // Create a scale transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), dialogForm);
        fadeTransition.setFromValue(1); // Start from fully transparent
        fadeTransition.setToValue(0); // End at fully opaque
        fadeTransition.setOnFinished(event -> {
            try {
                // Load the new root
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/menu.fxml"));
                Parent root = loader.load();
                // Get the current stage
                Stage stage = (Stage) usernameInputField.getScene().getWindow();
                // Set the new root
                stage.getScene().setRoot(root);
            } catch (IOException e) {
                log.error("Failed to load new layout", e);
                throw new RuntimeException(e);
            }
        });
        fadeTransition.play(); // Start the fade-in transition
    }

}