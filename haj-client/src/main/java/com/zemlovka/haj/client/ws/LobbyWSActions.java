package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.ws.commands.*;
import com.zemlovka.haj.utils.dto.client.*;
import com.zemlovka.haj.utils.dto.server.*;

import java.util.concurrent.Future;


public class LobbyWSActions {

    private final Commands commands;
    private final LobbyClient client;

    public LobbyWSActions() {
        LobbyClient lobbyClient = new LobbyClient();
        lobbyClient.start();
        this.client = lobbyClient;
        commands = new Commands(lobbyClient);
    }


    public Future<LoginResponseDTO> login(String username) {
        return commands.login.run(new LoginDTO(username, client.getClientId()));
    }

    public Future<LogoutResponseDTO> logout() {
        return commands.logout.run(new LogoutDTO());
    }

    public Future<CreateLobbyResponseDTO> createLobby(Lobby lobby) {
        return commands.createLobby.run(
                new CreateLobbyDTO(lobby.getName(), lobby.getPassword(), lobby.getSize())
        );
    }

    public Future<LobbyListDTO> fetchLobbyList() {
        return commands.fetchLobby.run(new FetchLobbysDTO());
    }

    public Future<JoinLobbyResponseDTO> fetchLobbyList(Lobby lobby, String password) {
        return commands.joinLobby.run(new JoinLobbyDTO(lobby.getName(), password));
    }

    static class Commands {

        LoginClientCommand login;
        LogoutClientCommand logout;
        CreateLobbyClientCommand createLobby;
        FetchLobbyClientCommand fetchLobby;
        JoinLobbyClientCommand joinLobby;
        StartGameClientCommand startGame;
        public Commands(LobbyClient client) {
            login = new LoginClientCommand(client);
            logout = new LogoutClientCommand(client);
            createLobby = new CreateLobbyClientCommand(client);
            fetchLobby = new FetchLobbyClientCommand(client);
            joinLobby = new JoinLobbyClientCommand(client);
            startGame = new StartGameClientCommand(client);
        }
    }
}
