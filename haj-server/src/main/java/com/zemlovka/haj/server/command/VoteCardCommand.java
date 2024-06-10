package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.ChooseCardDTO;
import com.zemlovka.haj.utils.dto.client.VoteCardDTO;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;
import com.zemlovka.haj.utils.dto.secondary.PlayerDTO;
import com.zemlovka.haj.utils.dto.server.ChooseCardResponseDTO;
import com.zemlovka.haj.utils.dto.server.GetChosenCardsResponseDTO;
import com.zemlovka.haj.utils.dto.server.VoteCardResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class VoteCardCommand extends AbstractServerCommand<VoteCardDTO, VoteCardResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(VoteCardCommand.class);
    private static final String NAME = CommandNameEnum.VOTE_CARD.name();
    private final User userData;

    public VoteCardCommand(ServerWsActions wsActions, User userData) {
        super(wsActions);
        this.userData = userData;
    }

    @Override
    public void execute(VoteCardDTO argument, ConnectionHeader clientHeader) {
        final Lobby lobby = userData.getCurrentLobby();
        lobby.getCurrentRound().voteCard(userData, argument.cardId());
        if (lobby.getCurrentRound().getVotedCards().size() == lobby.getUsers().size())
            lobby.getFlags().voteCards().signal();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
