package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import com.zemlovka.haj.client.ws.Player;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the lobby. Handles all the lobby logic.
 * <p>
 *
 * @author Nikita Korotov
 * @version 1.0
 */
public class LobbyController {

    @FXML
    private Button backButton;

    @FXML
    private VBox dialogForm;
    @FXML
    private VBox playersContainer;
    @FXML
    private HBox questionCardContainer;
    @FXML
    private HBox answerCardsContainer;
    @FXML
    private HBox playerCardsContainer;
    @FXML
    private ScrollPane answerCardsScroll;
    @FXML
    private ScrollPane playerCardsScroll;
    @FXML
    private VBox myCardsSection;

    private final AppState appState = AppState.getInstance();
    private static final Logger log = LoggerFactory.getLogger(LobbyController.class);

    @FXML
    private void initialize() {
        log.info("Lobby controller started.");

        /*
        We really need these 4 lines. Bug caused by the requestLayout() method which is set/called via ScrollPaneBehavior
        More: https://stackoverflow.com/questions/53603250/javafx-how-to-prevent-label-text-resize-during-scrollpane-focus
        */
        answerCardsScroll.setOnMousePressed(Event::consume);
        answerCardsContainer.setOnMousePressed(Event::consume);
        playerCardsScroll.setOnMousePressed(Event::consume);
        myCardsSection.setOnMousePressed(Event::consume);

        renderQuestionCard();
        renderPlayers(createPlayerList());
        renderAnswerCards(answerPlaceholderStrings());
        renderPlayerCards(answerPlaceholderStrings());
    }

    /**
     * @param
     */
    private void renderQuestionCard() {
        questionCardContainer.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/questionCard.fxml"));
            Pane questionCard = loader.load();
            //LobbyComponentController controller = loader.getController();
            //controller.setLobby();
            questionCardContainer.getChildren().add(questionCard);
        } catch (IOException e) {
            log.error("Error loading lobby component", e);
        }

    }
    private void renderPlayers(List<Player> playerList) {
        playersContainer.getChildren().clear();

        for (Player player : playerList) {
                //FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/lobbyComponent.fxml"));
                //HBox lobbyComponent = loader.load();
                //LobbyComponentController controller = loader.getController();

                //controller.setLobby(lobby);
                Label playerComponent = new Label(player.getUsername());
                if(player.isClient()){
                    playerComponent.setText(playerComponent.getText() + " (You)");
                }
            //playerComponent.setWrapText(true);

                playersContainer.getChildren().add(playerComponent);
        }
    }

    private List<Player> createPlayerList() {
        return List.of(new Player("Misha loh", "1", true),
                new Player("test", "2", false),
                new Player("Go lol", "3", false),
                new Player("Ya ustal", "4", false),
                new Player("pls help", "5", false));
    }
    @FXML
    private void renderAnswerCards(List<String> answersList) {
        answerCardsContainer.getChildren().clear();
        for (String answer : answersList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/answerCard.fxml"));
                Pane answerCard = loader.load();
                AnswerCardController controller = loader.getController();
                controller.setCard(answer);
                answerCardsContainer.getChildren().add(answerCard);
            } catch (IOException e) {
                log.error("Error loading lobby component", e);
            }
        }
    }
    @FXML
    private void renderPlayerCards(List<String> answersList) {
        playerCardsContainer.getChildren().clear();
        for (String answer : answersList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/answerCard.fxml"));
                Pane answerCard = loader.load();
                AnswerCardController controller = loader.getController();
                controller.setCard(answer);
                Pane pane = new Pane(answerCard);
                //playerCardsContainer.getChildren().add(pane);
                //answerCard.setClip(new Rectangle(200, 100));

                playerCardsContainer.getChildren().add(answerCard);
            } catch (IOException e) {
                log.error("Error loading lobby component", e);
            }
        }
    }

    private List<String> answerPlaceholderStrings() {
        return List.of("Poor life choices.",
                "Alcoholism.",
                "Therapy.",
                "Prescription drugs.",
                "The devil himself.");
    }


    /**
     * Function to return to the menu screen. Used by back button.
     */
    @FXML
    private void goMenu() {
        try {
            LayoutUtil.changeLayoutWithFadeTransition((Stage) backButton.getScene().getWindow(), "/com/zemlovka/haj/client/menu.fxml");
        } catch (IOException e) {
            log.error("Failed to return to the menu", e);
        }
    }
}
