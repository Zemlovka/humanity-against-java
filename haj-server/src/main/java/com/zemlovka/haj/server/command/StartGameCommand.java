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

import java.util.Collection;
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
        lobby.getFlags().lobbyReady().onSignal(f -> {
            logger.info("Signal received for StartGame flag for user {}, sending...", GlobalUtils.compileUUID(clientHeader.clientID()));
            CardDTO questionCard = lobby.getCurrentRound().getQuestionCard();
            lobby.refreshAnswerCards(userData);
            List<CardDTO> answerCard = lobby.getAnswerCards(userData).values().stream().toList();
            send(new StartGameResponseDTO(answerCard, questionCard), clientHeader);
            return null;
        });
        Collection<User> users = lobby.getUsers().values();
        if (users.size() == lobby.getCapacity()) {
            lobby.nextRound();
            logger.info("Signaling flag StartGame from user {}", GlobalUtils.compileUUID(clientHeader.clientID()));
            lobby.getFlags().lobbyReady().signal();
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
