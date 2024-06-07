package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.DtoMapper;
import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.FetchLobbysDTO;
import com.zemlovka.haj.utils.dto.server.LobbyListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class FetchLobbysCommand extends AbstractServerCommand<FetchLobbysDTO, LobbyListDTO> {
    private static final Logger logger = LoggerFactory.getLogger(FetchLobbysCommand.class);
    private static final String NAME = CommandNameEnum.FETCH_LOBBIES.name();
    private final ConcurrentHashMap<String, Lobby> lobbies;

    public FetchLobbysCommand(ServerWsActions wsActions, ConcurrentHashMap<String, Lobby> lobbies) {
        super(wsActions);
        this.lobbies = lobbies;
    }

    @Override
    public void execute(FetchLobbysDTO argument, ConnectionHeader clientHeader) {
        LobbyListDTO response = new LobbyListDTO(
                lobbies.values().stream().map(DtoMapper::mapLobby).collect(Collectors.toSet())
        );
        send(response, clientHeader);
    }


    @Override
    public String getName() {
        return NAME;
    }
}