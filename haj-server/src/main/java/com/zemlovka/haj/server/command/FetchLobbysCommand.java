package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.user.Lobby;
import com.zemlovka.haj.server.user.User;
import com.zemlovka.haj.utils.dto.client.FetchLobbysDTO;
import com.zemlovka.haj.utils.dto.secondary.LobbyDTO;
import com.zemlovka.haj.utils.dto.server.LobbyListDTO;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


public class FetchLobbysCommand implements ResolvableCommand<FetchLobbysDTO, LobbyListDTO> {
    private static final String NAME = "FetchLobby";

    private final ConcurrentHashMap<String, Lobby> lobbies;

    public FetchLobbysCommand(ConcurrentHashMap<String, Lobby> lobbies) {
        this.lobbies = lobbies;
    }

    @Override
    public LobbyListDTO run(FetchLobbysDTO argument) {
        return new LobbyListDTO(lobbies.values().stream().map(lobby -> new LobbyDTO(lobby.getName(), lobby.getCapacity(), lobby.getUsers().size(), lobby.getPassword() != null && !lobby.getPassword().isEmpty())).collect(Collectors.toSet()));
    }


    @Override
    public String getName() {
        return NAME;
    }
}