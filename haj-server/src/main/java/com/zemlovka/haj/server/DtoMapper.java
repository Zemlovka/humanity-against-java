package com.zemlovka.haj.server;

import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.dto.secondary.LobbyDTO;
import com.zemlovka.haj.utils.dto.secondary.PlayerDTO;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public class DtoMapper {
    public static LobbyDTO mapLobby(Lobby lobby) {
        return new LobbyDTO(lobby.getName(),
                lobby.getCapacity(),
                mapPlayers(lobby.getUsers().values()),
                lobby.getPassword() != null && !lobby.getPassword().isEmpty());
    }

    public static Set<PlayerDTO> mapPlayers(Collection<User> players) {
        return players.stream().map(u -> new PlayerDTO(u.getUsername(), u.getUuid())).collect(Collectors.toSet());
    }
}
