package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.AnswerCard;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    @FXML
    private Label logo;

    private AppState appState = AppState.getInstance();

    private AnswerCard answerCard;

    @FXML
    private void initialize() {
        log.info("AnswerCard component initialized.");
        addHoverAnimation(answerCardPane);
    }

    /**
     * Sets the answer to be displayed on the card
     */
    public void setCard(AnswerCard answerCard, AppState.State state) {
        this.answerCard = answerCard;
        if (state == AppState.State.CHOOSING) {
            answerText.setText("HUMANITY AGAINST JAVA");
            hideLogo();
        } else {
            answerText.setText(answerCard.getText());
        }
    }

    /**
     * Removes the logo from its parent container (card).
     */
    private void hideLogo() {
        logo.setOpacity(0);
    }

    public static void addHoverAnimation(Node node) {
        TranslateTransition transitionIn = new TranslateTransition(Duration.seconds(0.2), node);
        TranslateTransition transitionOut = new TranslateTransition(Duration.seconds(0.2), node);

        transitionIn.setToY(-3);
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
        log.info("ANSWER CARD was clicked {}", answerText.getText());
        if (appState.getCurrentState() == AppState.State.VOTING && appState.isCanVote()) {
            appState.setCanVote(false);
            Platform.runLater(() -> {
                //answerCardPane.setStyle("-fx-background-color: #FFD700; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.75), 10, 0.5, 0, 0);");

                // Create a DropShadow effect
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.BLACK);
                dropShadow.setRadius(10);
                dropShadow.setSpread(0.5);
                // Create a Glow effect
                Glow glow = new Glow();
                glow.setLevel(0.6);
                // Combine the Glow and DropShadow effects
                dropShadow.setInput(glow);

                BoxBlur blurEffect = new BoxBlur();
                blurEffect.setWidth(5);
                blurEffect.setHeight(5);
                blurEffect.setIterations(1);


                // Apply the combined effect to the answerCardPane
                answerCardPane.setEffect(dropShadow);
                answerCardPane.getParent().getChildrenUnmodifiable().forEach(node -> {
                    if (!(node == answerCardPane)) {
                        node.setEffect(blurEffect);
                    }
                });
            });
            wsActions.voteCard(answerCard).thenApply(f -> {
                return null;
            }).exceptionally(e -> {
                log.error("Error voting card", e);
                return null;
            });
        }

    }

}
