package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.DtoMapper;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.FetchLobbysDTO;
import com.zemlovka.haj.utils.dto.server.LobbyListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class FetchLobbysCommand implements ResolvableCommand<FetchLobbysDTO, LobbyListDTO> {
    private static final Logger logger = LoggerFactory.getLogger(FetchLobbysCommand.class);
    private static final String NAME = CommandNameEnum.FETCH_LOBBY.name();
    private final ConcurrentHashMap<String, Lobby> lobbies;

    public FetchLobbysCommand(ConcurrentHashMap<String, Lobby> lobbies) {
        this.lobbies = lobbies;
    }

    @Override
    public LobbyListDTO run(FetchLobbysDTO argument) {
        return new LobbyListDTO(lobbies.values().stream().map(DtoMapper::mapLobby).collect(Collectors.toSet())
        );
    }


    @Override
    public String getName() {
        return NAME;
    }
}