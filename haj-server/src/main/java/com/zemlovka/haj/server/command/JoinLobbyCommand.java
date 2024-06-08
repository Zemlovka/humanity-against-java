package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.DtoMapper;
import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.JoinLobbyDTO;
import com.zemlovka.haj.utils.dto.server.JoinLobbyResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;


public class JoinLobbyCommand extends AbstractServerCommand<JoinLobbyDTO, JoinLobbyResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(JoinLobbyCommand.class);
    private static final String NAME = CommandNameEnum.JOIN_LOBBY.name();
    private final User userData;
    private final ConcurrentHashMap<String, Lobby> lobbies;

    public JoinLobbyCommand(ServerWsActions wsActions, ConcurrentHashMap<String, Lobby> lobbies, User userData) {
        super(wsActions);
        this.lobbies = lobbies;
        this.userData = userData;
    }
    @Override
    public void execute(JoinLobbyDTO argument, ConnectionHeader clientHeader) {
        Lobby lobby = lobbies.get(argument.lobbyName());
        final JoinLobbyResponseDTO response;
        if (lobby == null) {
            response = new JoinLobbyResponseDTO(JoinLobbyResponseDTO.JoinState.LOBBY_DOES_NOT_EXIST, null);
        }
        else if (!lobby.getPassword().equals(argument.password())) {
            response = new JoinLobbyResponseDTO(JoinLobbyResponseDTO.JoinState.WRONG_PASSWORD, null);
        }
        else if (lobby.getCapacity() <= lobby.getUsers().size()) {
            response = new JoinLobbyResponseDTO(JoinLobbyResponseDTO.JoinState.LOBBY_FULL, null);
        }
        else{
            userData.setCurrentLobby(lobby);
            lobby.addUser(userData);
            lobby.getFlags().getNewPlayerFlag().complete(null);
            response = new JoinLobbyResponseDTO(JoinLobbyResponseDTO.JoinState.SUCCESS, DtoMapper.mapLobby(lobby));
        }
        send(response, clientHeader);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
