package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.AnswerCard;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the asnwerCard component, which represents answer (orange) card in the game
 *
 * @author Korotov Nikita
 * @version 1.0
 */
public class AnswerCardController extends AbstractWsActionsSettingController {
    private static final Logger log = LoggerFactory.getLogger(AnswerCardController.class);
    @FXML
    private Label answerText;
    @FXML
    private AnchorPane answerCardPane;
    private AnswerCard answerCard;

    @FXML
    private void initialize() {
        log.info("AnswerCard component initialized.");
        addHoverAnimation(answerCardPane);
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
        TranslateTransition transitionIn = new TranslateTransition(Duration.seconds(0.2), node);
        TranslateTransition transitionOut = new TranslateTransition(Duration.seconds(0.2), node);

        transitionIn.setToY(-10);
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
    private void onCardClick(Event event){
        log.info("ANSWER CARD was clicked {}", answerText.getText());

    }

}
