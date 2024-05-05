package com.zemlovka.haj.server;

import com.zemlovka.haj.server.user.Lobby;
import com.zemlovka.haj.utils.dto.secondary.LobbyDTO;
import com.zemlovka.haj.utils.dto.secondary.PlayerDTO;

import java.util.stream.Collectors;


public class DtoMapper {
    public static LobbyDTO mapLobby(Lobby lobby) {
        return new LobbyDTO(lobby.getName(),
                lobby.getCapacity(),
                lobby.getUsers().values().stream().map(u -> new PlayerDTO(u.getUsername())).collect(Collectors.toSet()),
                lobby.getPassword() != null && !lobby.getPassword().isEmpty());
    }
}
