package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.AnswerCard;
import com.zemlovka.haj.client.ws.WSActions;
import com.zemlovka.haj.client.ws.Player;
import com.zemlovka.haj.client.ws.QuestionCard;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;
import com.zemlovka.haj.utils.dto.secondary.PlayerDTO;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for handling layout changes
 * <p>
 *
 * @author Nikita Korotov
 * @version 1.0
 */
public class LayoutUtil {

    /**
     * Changes the layout of the stage to the layout specified by the fxml file
     *
     * @param stage The primary stage
     * @param fxml  The fxml file to load
     * @throws IOException If the fxml file cannot be loaded
     */
    public static void changeLayoutWithFadeTransition(Stage stage, String fxml, WSActions WSActions) throws IOException {
        Parent currentRoot = stage.getScene().getRoot();
        Parent newRoot = loadFXML(fxml, WSActions);
        fadeOutTransition(currentRoot, newRoot, stage);
    }

    /**
     * Fades out the current root
     *
     * @param currentRoot The current root
     * @param newRoot     The new root
     * @param stage       The primary stage
     */
    private static void fadeOutTransition(Parent currentRoot, Parent newRoot, Stage stage) {
        Node transitionNode = currentRoot.lookup("#dialogForm");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), transitionNode);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> stage.getScene().setRoot(newRoot));
        fadeTransition.play(); // Start the fade-out transition
    }

    /**
     * Loads the fxml file specified by the path
     *
     * @param fxml The path to the fxml file
     * @return The parent node of the fxml file
     * @throws IOException If the fxml file cannot be loaded
     */
    private static Parent loadFXML(String fxml, WSActions WSActions) throws IOException {
        FXMLLoader loader = new FXMLLoader(LayoutUtil.class.getResource(fxml));
        if (loader.getController() != null)
            ((AbstractWsActionsSettingController) loader.getController()).setWsActions(WSActions);
        Parent parent = loader.load();
        ((AbstractWsActionsSettingController) loader.getController()).setWsActions(WSActions);
        return parent;
    }

    /**
     * Fades in the node, used mainly on component's initialization
     *
     * @param node The node to fade in
     */
    public static void fadeInTransition(Parent node) {
        node.setOpacity(0);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * Shows new stage with an alert with the specified type, title and content text
     *
     * @param alertType   The type of the alert
     * @param title       The title of the alert
     * @param contentText The content text of the alert
     * @return true if OK is pressed, false otherwise (for confirmation alerts)
     */
    public static boolean showAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.setAlwaysOnTop(true);
        //alertStage.initStyle(StageStyle.UNDECORATED);
        Scene alertScene = alert.getDialogPane().getScene();
        alertScene.getRoot().getStylesheets().add(Objects.requireNonNull(LayoutUtil.class.getResource("/com/zemlovka/haj/client/alert.css")).toExternalForm());

        alert.getDialogPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                alert.close();
            }
        });

        alert.showAndWait();

        return alert.getResult() == ButtonType.OK;
    }

    public static List<Player> mapPlayers(Set<PlayerDTO> playerDTOSet, Player currentPlayer) {
        return playerDTOSet.stream().map(p -> {
            if (currentPlayer.getUsername().equals(p.getName()))
                return currentPlayer;
            else
                return new Player(p.getName(), null, false);
        }).collect(Collectors.toList());
    }

    public static QuestionCard mapQuestionCard(CardDTO cardDTO) {
        return new QuestionCard(cardDTO.getId(), cardDTO.getText());
    }

    public static List<AnswerCard> mapAnswerCards(List<CardDTO> cardDTOList) {
        return cardDTOList.stream().map(c -> new AnswerCard(c.getId(), c.getText())).collect(Collectors.toList());
    }
}
