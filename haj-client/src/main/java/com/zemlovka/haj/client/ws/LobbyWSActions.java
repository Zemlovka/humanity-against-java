package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.ws.commands.*;
import com.zemlovka.haj.utils.dto.client.*;
import com.zemlovka.haj.utils.dto.server.*;

import java.util.concurrent.CompletableFuture;
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


    public JavaFxAsyncFutureWrapper<LoginResponseDTO> login(String username) {
        return commands.login.run(new LoginDTO(username, client.getClientId()));
    }

    public JavaFxAsyncFutureWrapper<LogoutResponseDTO> logout() {
        return commands.logout.run(new LogoutDTO());
    }

    public JavaFxAsyncFutureWrapper<LeaveLobbyResponseDTO> leaveLobby() {
        return commands.leaveLobby.run(new LeaveLobbyDTO());
    }

    public JavaFxAsyncFutureWrapper<CreateLobbyResponseDTO> createLobby(Lobby lobby) {
        return commands.createLobby.run(
                new CreateLobbyDTO(lobby.getName(), lobby.getPassword(), lobby.getSize())
        );
    }

    public JavaFxAsyncFutureWrapper<LobbyListDTO> fetchLobbyList() {
        return commands.fetchLobby.run(new FetchLobbysDTO());
    }

    public JavaFxAsyncFutureWrapper<JoinLobbyResponseDTO> joinLobby(Lobby lobby, String password) {
        return commands.joinLobby.run(new JoinLobbyDTO(lobby.getName(), password));
    }
    public JavaFxAsyncFutureWrapper<StartGameResponseDTO> startGame() {
        return commands.startGame.run(new StartGameDTO());
    }
    public JavaFxAsyncFutureWrapper<FetchPlayersResponseDTO> fetchPlayers() {
        return commands.fetchPlayers.run(new FetchPlayersDTO());
    }

    static class Commands {

        final LoginClientCommand login;
        final LogoutClientCommand logout;
        final LeaveLobbyCommand leaveLobby;
        final CreateLobbyClientCommand createLobby;
        final FetchLobbyClientCommand fetchLobby;
        final JoinLobbyClientCommand joinLobby;
        final StartGameClientCommand startGame;
        final FetchPlayersCommand fetchPlayers;
        public Commands(LobbyClient client) {
            login = new LoginClientCommand(client);
            logout = new LogoutClientCommand(client);
            leaveLobby = new LeaveLobbyCommand(client);
            createLobby = new CreateLobbyClientCommand(client);
            fetchLobby = new FetchLobbyClientCommand(client);
            joinLobby = new JoinLobbyClientCommand(client);
            startGame = new StartGameClientCommand(client);
            fetchPlayers = new FetchPlayersCommand(client);
        }
    }
}
