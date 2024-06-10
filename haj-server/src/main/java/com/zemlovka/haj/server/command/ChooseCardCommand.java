package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.ChooseCardDTO;
import com.zemlovka.haj.utils.dto.server.ChooseCardResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChooseCardCommand extends AbstractServerCommand<ChooseCardDTO, ChooseCardResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(ChooseCardCommand.class);
    private static final String NAME = CommandNameEnum.CHOOSE_CARD.name();
    private final User userData;

    public ChooseCardCommand(ServerWsActions wsActions, User userData) {
        super(wsActions);
        this.userData = userData;
    }

    @Override
    public void execute(ChooseCardDTO argument, ConnectionHeader clientHeader) {
        final Lobby lobby = userData.getCurrentLobby();
        if (!lobby.getAnswerCards(userData).containsKey(argument.cardId()))
            //todo
            logger.info("TODO CASE HAS BEEN ENCOUNTERED USER DOES NOT HAVE THE CARD THAT HE CHOSE");
        else {
            lobby.getCurrentRound().addChosenCard(userData, lobby.removeAnswerCards(userData, argument.cardId()));
            lobby.getFlags().chooseCards().signal();
            send(new ChooseCardResponseDTO(), clientHeader);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
