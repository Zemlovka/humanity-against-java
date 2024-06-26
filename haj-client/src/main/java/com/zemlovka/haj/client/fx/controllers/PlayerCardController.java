package com.zemlovka.haj.client.fx.controllers;

import com.zemlovka.haj.client.fx.AppState;
import com.zemlovka.haj.client.ws.entities.AnswerCard;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the playerCard component (answer card hold by a player), which represents answer (orange) card in the game
 *
 * @author Korotov Nikita
 * @version 1.0
 */
public class PlayerCardController extends AbstractWsActionsSettingController{
    private static final Logger log = LoggerFactory.getLogger(PlayerCardController.class);
    @FXML
    private Label answerText;
    @FXML
    private AnchorPane answerCardPane;

    private AnswerCard playerCard;
    private AppState appState = AppState.getInstance();

    @FXML
    private void initialize() {
        log.info("AnswerCard component initialized.");
        renderCard();
        addHoverAnimation(answerCardPane);
    }

    /**
     * Sets the answer to be displayed on the card
     *
     */
    public void setCard(AnswerCard answerCard) {
        this.playerCard = answerCard;
    }
    public void renderCard(){
        answerText.setText(playerCard.getText());
    }
    public static void addHoverAnimation(Node node) {
        TranslateTransition transitionIn = new TranslateTransition(Duration.seconds(0.2), node);
        TranslateTransition transitionOut = new TranslateTransition(Duration.seconds(0.2), node);

        transitionIn.setToY(-30);
        transitionOut.setToY(0);

        node.setOnMouseEntered(event -> {
            transitionOut.stop();
            transitionIn.play();
        });

        node.setOnMouseExited(event -> {
            transitionIn.stop();
            transitionOut.play();
        });
    }
    @FXML
    private void onCardClick(Event event) {
        log.info("PLAYER CARD was clicked {}", answerText.getText());
        if(appState.getCurrentState() == AppState.State.CHOOSING && appState.isCanChoose()){
            appState.setCanChoose(false);
            wsActions.chooseCard(playerCard);
            removeCardPaneFromParent();
        }
    }

    /**
     * Removes the answerCardPane from its parent container.
     */
    private void removeCardPaneFromParent() {
        Pane parent = (Pane) answerCardPane.getParent();
        if (parent != null) {
            parent.getChildren().remove(answerCardPane);
        }
    }
}
