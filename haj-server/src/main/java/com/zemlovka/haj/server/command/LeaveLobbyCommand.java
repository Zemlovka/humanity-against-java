package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.LeaveLobbyDTO;
import com.zemlovka.haj.utils.dto.server.LeaveLobbyResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class LeaveLobbyCommand extends AbstractServerCommand<LeaveLobbyDTO, LeaveLobbyResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(LeaveLobbyCommand.class);
    private static final String NAME = CommandNameEnum.LEAVE_LOBBY.name();
    private final User userData;
    private final Map<String, Lobby> lobbies;

    public LeaveLobbyCommand(ServerWsActions wsActions, Map<String, Lobby> lobbies, User userData) {
        super(wsActions);
        this.lobbies = lobbies;
        this.userData = userData;
    }
    @Override
    public void execute(LeaveLobbyDTO argument, ConnectionHeader clientHeader) {
        //todo
        final LeaveLobbyResponseDTO response;
        final Lobby lobby = userData.getCurrentLobby();
        lobby.removeUser(userData);
        lobby.getFlags().newPlayer().signal();
        if (lobby.getUsers().size() == 0)
            lobbies.remove(lobby.getName());
        userData.setCurrentLobby(null);
        response = new LeaveLobbyResponseDTO();
        send(response, clientHeader);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
