package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.DtoMapper;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.JoinLobbyDTO;
import com.zemlovka.haj.utils.dto.server.JoinLobbyResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;


public class JoinLobbyCommand implements ResolvableCommand<JoinLobbyDTO, JoinLobbyResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(JoinLobbyCommand.class);
    private static final String NAME = CommandNameEnum.JOIN_LOBBY.name();
    private final User userData;
    private final ConcurrentHashMap<String, Lobby> lobbies;

    public JoinLobbyCommand(ConcurrentHashMap<String, Lobby> lobbies, User userData) {
        this.lobbies = lobbies;
        this.userData = userData;
    }
    @Override
    public JoinLobbyResponseDTO run(JoinLobbyDTO argument) {
        Lobby lobby = lobbies.get(argument.lobbyName());
        if (lobby == null)
            return new JoinLobbyResponseDTO(false, null);
        if (!lobby.getPassword().equals(argument.password()))
            return new JoinLobbyResponseDTO(false, null);
        lobby.getUsers().put(userData.getUuid(), userData);
        return new JoinLobbyResponseDTO(true, DtoMapper.mapLobby(lobby));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
