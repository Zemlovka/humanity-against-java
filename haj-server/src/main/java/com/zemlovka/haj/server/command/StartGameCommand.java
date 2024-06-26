package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.GlobalUtils;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.StartGameDTO;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;
import com.zemlovka.haj.utils.dto.server.StartGameResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class StartGameCommand extends AbstractServerCommand<StartGameDTO, StartGameResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(StartGameCommand.class);
    private static final String NAME = CommandNameEnum.START_GAME.name();
    private final User userData;

    public StartGameCommand(ServerWsActions wsActions, User userData) {
        super(wsActions);
        this.userData = userData;
    }

    @Override
    public void execute(StartGameDTO argument, ConnectionHeader clientHeader) {
        final Lobby lobby = userData.getCurrentLobby();
        userData.setReady(true);
        lobby.getFlags().lobbyReady().onSignal(f -> {
            lobby.getUsers().values().forEach(u -> u.setReady(false));
            if (lobby.isRoundPlayable()) {
                try {
                    logger.info("Signal received for StartGame flag for user {}, sending...", GlobalUtils.compileUUID(clientHeader.clientID()));
                    CardDTO questionCard = lobby.getCurrentRound().getQuestionCard();
                    lobby.refreshAnswerCards(userData);
                    List<CardDTO> answerCard = lobby.getAnswerCards(userData).values().stream().toList();
                    send(new StartGameResponseDTO(false, answerCard, questionCard, lobby.getCurrentRound().getRoundNumber()), clientHeader);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                lobby.setGameActive(false);
                send(new StartGameResponseDTO(true, null, null, null), clientHeader);
            }
            return null;
        });
        synchronized (lobby) {
            List<User> readyUsers = lobby.getUsers().values().stream().filter(User::isReady).toList();
            if (readyUsers.size() == lobby.getCapacity()) {
                lobby.nextRound();
                logger.info("Signaling flag StartGame from user {}", GlobalUtils.compileUUID(clientHeader.clientID()));
                lobby.getFlags().lobbyReady().signal();
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
