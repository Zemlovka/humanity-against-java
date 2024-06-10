package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.*;
import com.zemlovka.haj.utils.dto.server.FetchPlayersResponseDTO;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.zemlovka.haj.client.fx.AppState.State.CHOOSING;
import static com.zemlovka.haj.client.fx.AppState.State.VOTING;

/**
 * Controller for the lobby. Handles all the lobby logic.
 * <p>
 *
 * @author Nikita Korotov
 * @version 1.0
 */
public class LobbyController extends AbstractWsActionsSettingController {

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
    private final Lobby lobby = appState.getCurrentLobby();
    private static final Logger log = LoggerFactory.getLogger(LobbyController.class);


    @FXML
    private void initialize() {
        log.info("Lobby controller started.");
        LayoutUtil.fadeInTransition(dialogForm);

        /*
        We really need these 4 lines. Bug caused by the requestLayout() method which is set/called via ScrollPaneBehavior
        More: https://stackoverflow.com/questions/53603250/javafx-how-to-prevent-label-text-resize-during-scrollpane-focus
        */
        answerCardsScroll.setOnMousePressed(Event::consume);
        answerCardsContainer.setOnMousePressed(Event::consume);
        playerCardsScroll.setOnMousePressed(Event::consume);
        myCardsSection.setOnMousePressed(Event::consume);
        renderPlayers(appState.getCurrentLobby().getPlayers());
        showSpinner();
    }

    /**
     * @param
     */
    private void renderQuestionCard(QuestionCard questionCard) {
        questionCardContainer.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/questionCard.fxml"));
            Pane questionCardPane = loader.load();
            QuestionCardController controller = loader.getController();
            lobby.setQuestionCard(questionCard);
            controller.setCard(lobby.getQuestionCard().getText());
            questionCardContainer.getChildren().add(questionCardPane);
        } catch (IOException e) {
            log.error("Error loading lobby component", e);
        }

    }

    private void renderPlayers(List<Player> playerList) {
        log.info("Rendering players: " + playerList.stream().map(Player::getUsername).collect(Collectors.joining(", ")));
        playersContainer.getChildren().clear();

        for (Player player : playerList) {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/lobbyComponent.fxml"));
            //HBox lobbyComponent = loader.load();
            //LobbyComponentController controller = loader.getController();

            //controller.setLobby(lobby);
            Label playerComponent = new Label(player.getUsername());
            log.info("rendering {}", player.getUsername());
            if (player.isClient()) {
                playerComponent.setText(playerComponent.getText() + " (You)");
            }
            //playerComponent.setWrapText(true);

            playersContainer.getChildren().add(playerComponent);
        }
    }

    private void registerFetchPlayer() {
        wsActions.fetchPlayers().thenApply(f -> {
            Platform.runLater(() -> {
                renderPlayers(LayoutUtil.mapPlayers(f.players(), appState.getCurrentPlayer()));
            });
            return null;
        }).exceptionally(e -> {
            log.error("Error fetching players", e);
            return null;
        });
        startGame();
    }

    private void registerChosenCards() {
        wsActions.getChosenCards().thenApply(f -> {
            Platform.runLater(() -> {
                if (!f.awaitFurtherCards()) {
                    startVoting();
                }
                renderAnswerCards(LayoutUtil.mapAnswerCards(f.cards()));
            });
            return null;
        }).exceptionally(e -> {
            log.error("Error fetching answered cards", e);
            return null;
        });
    }

    private void startGame() {
        wsActions.startGame().thenApply(f -> {
            Platform.runLater(() -> {
                if (!f.gameEnd()) {
                    clearAnswerCards();
                    log.info("The round in '{}' lobby was started", lobby.getName());
                    appState.setCurrentState(CHOOSING);
                    appState.setCanVote(false);
                    appState.setCanChoose(true);
                    removeSpinner();
                    renderQuestionCard(LayoutUtil.mapQuestionCard(f.questionCard()));
                    renderPlayerCards(LayoutUtil.mapAnswerCards(f.answerCards()));
                    registerChosenCards();
                } else {
                    //todo end game
                }
            });
            return null;
        }).exceptionally(e -> {
            log.error("Error starting game", e);
            return null;
        });
    }
    @FXML
    private void clearAnswerCards(){
        answerCardsContainer.getChildren().clear();
    }

    @FXML
    private void renderAnswerCards(List<AnswerCard> answerCards) {
        answerCardsContainer.getChildren().clear();
        for (AnswerCard card : answerCards) {
            try {
                String url = "/com/zemlovka/haj/client/answerCard.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
                AnswerCardController controller = new AnswerCardController();
                loader.setController(controller); //defining controller since there are 2 controllers for answerCards.fxml
                controller.setWsActions(wsActions);
                Pane answerCard = loader.load();
                controller.setCard(card, appState.getCurrentState());
                answerCardsContainer.getChildren().add(answerCard);

            } catch (IOException e) {
                log.error("Error loading lobby component", e);
            }
        }
    }

    private void startVoting() {
        appState.setCurrentState(VOTING);
        appState.setCanVote(true);
        appState.setCanChoose(false);
        appState.getNotificationService().createToast(dialogForm.getScene().getWindow(), "Choose the best answer!", ToastNotification.Position.RIGHT_BOTTOM, true, true, false).showToast();
        log.info("Voting has been started in the lobby '{}'", lobby.getName());

        wsActions.getWinnerCard().thenApply(f -> {
            if (f.winnerCard() != null) {
                Platform.runLater(() -> {
                    appState.getNotificationService().createToast(dialogForm.getScene().getWindow(), "The winner is: " + f.winnerPlayer().getName(), ToastNotification.Position.RIGHT_BOTTOM, false, true, false).showToast();
                });
                startGame();
            }
            return null;
        }).exceptionally(e -> {
            log.error("Error fetching winner card", e);
            return null;
        });
    }

    @FXML
    private void renderPlayerCards(List<AnswerCard> answersCards) {
        playerCardsContainer.getChildren().clear();

        Timeline timeline = new Timeline();
        Duration delayBetweenCards = Duration.millis(500); // Delay of 500ms between cards

        for (int i = 0; i < answersCards.size(); i++) {
            AnswerCard card = answersCards.get(i);
            KeyFrame keyFrame = new KeyFrame(
                    delayBetweenCards.multiply(i),
                    event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zemlovka/haj/client/answerCard.fxml"));
                            PlayerCardController controller = new PlayerCardController();
                            loader.setController(controller);
                            controller.setCard(card);
                            controller.setWsActions(wsActions);

                            Pane answerCard = loader.load();
                            answerCard.setTranslateY(-1000); // Start off the screen above

                            playerCardsContainer.getChildren().add(answerCard);

                            // Create translation animation
                            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), answerCard);
                            translateTransition.setFromY(-1000);
                            translateTransition.setToY(0);

                            // Add slight rotation effect
                            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), answerCard);
                            rotateTransition.setByAngle(10);
                            rotateTransition.setCycleCount(2);
                            rotateTransition.setAutoReverse(true);

                            // Combine translation and rotation
                            ParallelTransition parallelTransition = new ParallelTransition(answerCard, translateTransition, rotateTransition);
                            parallelTransition.setInterpolator(Interpolator.EASE_OUT);

                            parallelTransition.play();

                        } catch (IOException e) {
                            log.error("Error loading lobby component", e);
                        }
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
    }

    private void showSpinner() {
        VBox container = new VBox();
        container.setAlignment(javafx.geometry.Pos.CENTER);
        container.setSpacing(15);

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setMaxSize(50, 50);
        spinner.setStyle("-fx-progress-color: #E37107;");

        Label label = new Label("Waiting for other players...");

        answerCardsContainer.getChildren().clear();
        container.getChildren().add(spinner);
        container.getChildren().add(label);
        container.setId("spinner");
        answerCardsContainer.getChildren().add(container);
    }

    private void removeSpinner() {
        Node spinnerNode = answerCardsContainer.lookup("#spinner");
        answerCardsContainer.getChildren().remove(spinnerNode);
    }

    /**
     * Function to return to the menu screen. Used by back button.
     */
    @FXML
    private void goMenu() {
        try {
            if (LayoutUtil.showAlert(Alert.AlertType.CONFIRMATION, "You are about to leave the lobby", "Proceed leaving lobby?")) {
                LayoutUtil.changeLayoutWithFadeTransition((Stage) backButton.getScene().getWindow(), "/com/zemlovka/haj/client/menu.fxml", wsActions);
            }
        } catch (IOException e) {
            log.error("Failed to return to the menu", e);
        }
    }

    @Override
    void setWsActions(WSActions wsActions) {
        super.setWsActions(wsActions);
        registerFetchPlayer();
    }
}
