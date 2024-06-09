package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.GetChosenCardsDTO;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;
import com.zemlovka.haj.utils.dto.server.GetChosenCardsResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;


public class GetChosenCardsCommand extends AbstractServerCommand<GetChosenCardsDTO, GetChosenCardsResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(GetChosenCardsCommand.class);
    private static final String NAME = CommandNameEnum.GET_CHOSEN_CARDS.name();
    private final User userData;

    public GetChosenCardsCommand(ServerWsActions wsActions, User userData) {
        super(wsActions);
        this.userData = userData;
    }

    @Override
    public void execute(GetChosenCardsDTO argument, ConnectionHeader clientHeader) {
        final Lobby lobby = userData.getCurrentLobby();
        lobby.getFlags().chooseCards().onSignal(f -> {
            List<CardDTO> chosenCards = lobby.getCurrentRound().getChosenCards().values().stream().toList();
            send(new GetChosenCardsResponseDTO(chosenCards), clientHeader);
            return null;
        });
        userData.setReady(true);
        Collection<User> users = lobby.getUsers().values();
        int userNumber = users.size();
        int readyUsers = (int) users.stream().filter(User::isReady).count();
        if (userNumber == readyUsers) {
            lobby.nextRound();
            lobby.getFlags().lobbyReady().signal();
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}