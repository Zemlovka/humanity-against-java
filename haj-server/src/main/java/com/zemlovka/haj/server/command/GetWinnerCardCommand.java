package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.GetWinnerCardDTO;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;
import com.zemlovka.haj.utils.dto.secondary.PlayerDTO;
import com.zemlovka.haj.utils.dto.server.GetWinnerCardResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Returns the winner card, is being set of by the VoteCardCommand from all users
 */
public class GetWinnerCardCommand extends AbstractServerCommand<GetWinnerCardDTO, GetWinnerCardResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(GetWinnerCardCommand.class);
    private static final String NAME = CommandNameEnum.GET_WINNER_CARD.name();
    private final User userData;

    public GetWinnerCardCommand(ServerWsActions wsActions, User userData) {
        super(wsActions);
        this.userData = userData;
    }

    @Override
    public void execute(GetWinnerCardDTO argument, ConnectionHeader clientHeader) {
        final Lobby lobby = userData.getCurrentLobby();
        lobby.getFlags().voteCards().onSignal(f -> {
            Map.Entry<User, CardDTO> winnerEntry = lobby.getCurrentRound().getWinner();
            PlayerDTO winnerPlayer = new PlayerDTO(winnerEntry.getKey().getUsername(), winnerEntry.getKey().getUuid(), lobby.getPoints(winnerEntry.getKey()));
            send(new GetWinnerCardResponseDTO(winnerEntry.getValue(), winnerPlayer), clientHeader);
            return null;
        });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
