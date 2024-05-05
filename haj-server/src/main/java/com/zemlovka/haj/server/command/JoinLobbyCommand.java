package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.user.Lobby;
import com.zemlovka.haj.server.user.User;
import com.zemlovka.haj.utils.dto.client.JoinLobbyDTO;
import com.zemlovka.haj.utils.dto.server.JoinLobbyResponseDTO;

import java.util.concurrent.ConcurrentHashMap;


public class JoinLobbyCommand implements ResolvableCommand<JoinLobbyDTO, JoinLobbyResponseDTO> {
    private static final String NAME = "JoinLobby";
    private final User userData;
    private final ConcurrentHashMap<String, Lobby> lobbies;

    public JoinLobbyCommand(ConcurrentHashMap<String, Lobby> lobbies, User userData) {
        this.lobbies = lobbies;
        this.userData = userData;
    }
    @Override
    public JoinLobbyResponseDTO run(JoinLobbyDTO argument) {
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
