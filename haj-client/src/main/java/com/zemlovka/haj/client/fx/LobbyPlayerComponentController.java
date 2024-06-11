package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Controller for the asnwerCard component, which represents answer (orange) card in the game
 *
 * @author Korotov Nikita
 * @version 1.0
 */
public class LobbyPlayerComponentController extends AbstractWsActionsSettingController {
    private static final Logger log = LoggerFactory.getLogger(LobbyPlayerComponentController.class);
    @FXML
    private AnchorPane playerPane;
    @FXML
    private Label score;
    @FXML
    private Label username;
    @FXML
    private ImageView avatar;

    private AppState appState = AppState.getInstance();

    private Player player;

    @FXML
    private void initialize() {
        log.info("AnswerCard component initialized.");
    }
    public void setPlayer(Player player){
        this.player = player;
        username.setText(player.getUsername());
        score.setText(String.valueOf(player.getScore()));
        Image image = new Image(Objects.requireNonNull(LobbyPlayerComponentController.class.getResourceAsStream(
                "/com/zemlovka/haj/client/player.png")));
        avatar.setImage(image);

    }
}
