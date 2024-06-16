package com.zemlovka.haj.client.fx.controllers;

import com.zemlovka.haj.client.ws.entities.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Controller for the player component, which represents user avatar in the game
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

    @FXML
    private void initialize() {
        log.info("AnswerCard component initialized.");
    }

    /**
     * Sets the player to the component (avatar, nickname, score)
     * @param player player to set
     */
    public void setPlayer(Player player) {
        String nickname = player.getUsername();
        if (player.isClient()) {
            nickname = (player.getUsername() + " (You)");
        }
        username.setText(nickname);
        score.setText(String.valueOf(player.getScore()));
        Image image = new Image(Objects.requireNonNull(LobbyPlayerComponentController.class.getResourceAsStream(
                "/com/zemlovka/haj/client/player.png")));
        avatar.setImage(image);

    }
}
