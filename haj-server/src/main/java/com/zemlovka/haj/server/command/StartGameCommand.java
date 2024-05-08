package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.StartGameDTO;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;
import com.zemlovka.haj.utils.dto.server.StartGameResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;


public class StartGameCommand extends AbstractServerCommand<StartGameDTO, StartGameResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(LoginCommand.class);
    private static final String NAME = CommandNameEnum.LOGIN.name();
    private final User userData;
    private final Lobby lobby;

    public StartGameCommand(ServerWsActions wsActions, User userData) {
        super(wsActions);
        this.userData = userData;
        this.lobby = userData.getCurrentLobby();
    }

    @Override
    public void execute(StartGameDTO argument, ConnectionHeader clientHeader) {
        lobby.getFlags().getLobbyReadyFlag().thenApply(f -> {
            CardDTO questionCard = lobby.selectRandomQuestionCard();
            List<CardDTO> answerCard = lobby.selectRandomAnswerCards(Lobby.DEFAULT_PLAYER_CARDS_NUMBER);
            send(new StartGameResponseDTO(answerCard, questionCard), clientHeader);
            lobby.getFlags().clearLobbyReadyFlag();
            return null;
        });
        userData.setReady(true);
        Collection<User> users = lobby.getUsers().values();
        int userNumber = users.size();
        int readyUsers = (int) users.stream().filter(User::isReady).count();
        if (userNumber == readyUsers) {
            lobby.nextRound();
            lobby.getFlags().getLobbyReadyFlag().complete(null);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
