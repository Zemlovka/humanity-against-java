package com.zemlovka.haj.client.fx.controllers;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the questionCard component, which represents question (black) card in the game
 *
 * @author Korotov Nikita
 * @version 1.0
 */
public class QuestionCardController {
    private static final Logger log = LoggerFactory.getLogger(QuestionCardController.class);
    @FXML
    private Label cardText;
    @FXML
    private AnchorPane questionCard;

    @FXML
    private void initialize() {
        log.info("QuestionCard component initialized.");
        addHoverAnimation(questionCard);
        questionCard.setOnMouseClicked(mouseEvent -> {
            log.info("Question card clicked");
        });
    }

    /**
     * Sets the answer to be displayed on the card
     *
     * @param text the answer to be displayed
     */
    public void setCard(String text) {
        cardText.setText(text);
    }
    public static void addHoverAnimation(Node node) {


        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), node);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);

        node.setOnMouseEntered(event -> {
            scaleTransition.play();
        });

        node.setOnMouseExited(event -> {
            scaleTransition.stop();
            node.setTranslateY(0); // Reset translation when mouse exits
            node.setScaleX(1); // Reset scale when mouse exits
            node.setScaleY(1);
        });
    }
}
