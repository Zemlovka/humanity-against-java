package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the lobby component, which is a single lobby in the lobby list
 *
 * @author Korotov Nikita
 * @version 1.0
 * @see Lobby (entity)
 * @see FindLobbyController (parent controller)
 */
public class AnswerCardController {
    private static final Logger log = LoggerFactory.getLogger(AnswerCardController.class);
    @FXML
    private Label answerText;
    @FXML
    private AnchorPane answerCard;

    @FXML
    private void initialize() {
        log.info("AnswerCard component initialized.");
        addHoverAnimation(answerCard);
    }

    /**
     * Sets the answer to be displayed on the card
     *
     * @param answer the answer to be displayed
     */
    public void setCard(String answer) {
        answerText.setText(answer);
    }
    public static void addHoverAnimation(Node node) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), node);
        transition.setByY(-40);

        node.setOnMouseEntered(event -> {
            transition.play();
        });

        node.setOnMouseExited(event -> {
            transition.stop();
            node.setTranslateY(0); // Reset translation when mouse exits
        });
    }
}
