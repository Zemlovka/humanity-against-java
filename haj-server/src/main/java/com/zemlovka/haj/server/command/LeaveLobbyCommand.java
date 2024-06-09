package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.LeaveLobbyDTO;
import com.zemlovka.haj.utils.dto.server.LeaveLobbyResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LeaveLobbyCommand extends AbstractServerCommand<LeaveLobbyDTO, LeaveLobbyResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(LeaveLobbyCommand.class);
    private static final String NAME = CommandNameEnum.LEAVE_LOBBY.name();
    private final User userData;

    public LeaveLobbyCommand(ServerWsActions wsActions, User userData) {
        super(wsActions);
        this.userData = userData;
    }
    @Override
    public void execute(LeaveLobbyDTO argument, ConnectionHeader clientHeader) {
        //todo
        final LeaveLobbyResponseDTO response;
        userData.getCurrentLobby().removeUser(userData);
        userData.getCurrentLobby().getFlags().newPlayer().signal();
        userData.setCurrentLobby(null);
        response = new LeaveLobbyResponseDTO();
        send(response, clientHeader);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
