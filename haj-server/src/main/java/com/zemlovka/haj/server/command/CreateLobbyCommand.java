package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.CreateLobbyDTO;
import com.zemlovka.haj.utils.dto.server.CreateLobbyResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class CreateLobbyCommand extends AbstractServerCommand<CreateLobbyDTO, CreateLobbyResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(CreateLobbyCommand.class);
    private static final String NAME = CommandNameEnum.CREATE_LOBBY.name();
    private final User userData;
    private final ConcurrentHashMap<String, Lobby> lobbies;

    public CreateLobbyCommand(ServerWsActions wsActions, ConcurrentHashMap<String, Lobby> lobbies, User userData) {
        super(wsActions);
        this.lobbies = lobbies;
        this.userData = userData;
    }

    @Override
    public void execute(CreateLobbyDTO argument, ConnectionHeader clientHeader) {
        userData.shutdownLobbyPollingExecutor();
        final CreateLobbyResponseDTO response;
        if (!userData.isLoggedIn()) {
            logger.info("Lobby with name: {} was not created because user is not logged in", argument.name());
            response = new CreateLobbyResponseDTO(false);
        }
        else if (lobbies.containsKey(argument.name())) {
            logger.info("Lobby with name: {} was not created because this name has already been used for another lobby", argument.name());
            response = new CreateLobbyResponseDTO(false);
        } else {
            Lobby lobby = new Lobby(argument.size(), argument.name(), argument.password(), argument.roundNumber());
            userData.setCurrentLobby(lobby);
            lobby.addUser(userData);
            lobbies.put(lobby.getName(), lobby);
            //todo add sender for when new users are joining
            logger.info("Lobby: {} was created by user: {}", lobby.getName(), userData.getUsername());
            response = new CreateLobbyResponseDTO(true);
        }
        send(response, clientHeader);
    }


    @Override
    public String getName() {
        return NAME;
    }

}
