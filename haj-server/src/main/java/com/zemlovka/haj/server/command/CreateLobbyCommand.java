package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.user.Lobby;
import com.zemlovka.haj.server.user.User;
import com.zemlovka.haj.utils.dto.client.CreateLobbyDTO;
import com.zemlovka.haj.utils.dto.server.CreateLobbyResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class CreateLobbyCommand implements ResolvableCommand<CreateLobbyDTO, CreateLobbyResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(CreateLobbyCommand.class);
    private static final String NAME = "CreateLobby";
    private final User userData;
    private final ConcurrentHashMap<String, Lobby> lobbies;

    public CreateLobbyCommand(ConcurrentHashMap<String, Lobby> lobbies, User userData) {
        this.lobbies = lobbies;
        this.userData = userData;
    }

    @Override
    public CreateLobbyResponseDTO run(CreateLobbyDTO argument) {
        if (lobbies.containsKey(argument.name()))
            return new CreateLobbyResponseDTO(false);
        ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();
        users.put(userData.getUuid(), userData);
        Lobby lobby = new Lobby(argument.size(), argument.name(), argument.password(), users);
        lobbies.put(lobby.getName(), lobby);
        //todo add sender for when new users are joining
        logger.info("lobby {} was created by user {}", lobby.getName(), userData.getUsername());
        return new CreateLobbyResponseDTO(true);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
