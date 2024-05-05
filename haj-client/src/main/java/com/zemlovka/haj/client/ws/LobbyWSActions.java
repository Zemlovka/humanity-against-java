package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.ws.commands.CreateLobbyCommand;
import com.zemlovka.haj.client.ws.commands.FetchLobbyCommand;
import com.zemlovka.haj.client.ws.commands.InitCommand;
import com.zemlovka.haj.client.ws.commands.JoinLobbyCommand;
import com.zemlovka.haj.utils.dto.AcceptDTO;
import com.zemlovka.haj.utils.dto.client.CreateLobbyDTO;
import com.zemlovka.haj.utils.dto.client.FetchLobbysDTO;
import com.zemlovka.haj.utils.dto.client.InitDTO;
import com.zemlovka.haj.utils.dto.server.CreateLobbyResponseDTO;
import com.zemlovka.haj.utils.dto.server.LobbyListDTO;
import com.zemlovka.haj.utils.dto.server.UserConnectionResponseDTO;

import java.util.concurrent.Future;


public class LobbyWSActions {

    private LobbyClient client;


    private InitCommand initCommand;
    private CreateLobbyCommand createLobbyCommand;
    private FetchLobbyCommand fetchLobbyCommand;
    private JoinLobbyCommand joinLobbyCommand;
    public LobbyWSActions() {
        LobbyClient client = new LobbyClient();
        client.start(); //will it work?
        this.client = client;
        initCommand = new InitCommand(client);
        createLobbyCommand = new CreateLobbyCommand(client);
        fetchLobbyCommand = new FetchLobbyCommand(client);
        joinLobbyCommand = new JoinLobbyCommand(client);
    }

    public Future<UserConnectionResponseDTO> login(String username) {
        final InitDTO initDTO = new InitDTO(username, client.getClientId());
        return initCommand.run(initDTO);
    }

    public Future<UserConnectionResponseDTO> logout(String username) {
        // todo
        return null;
    }

    public Future<CreateLobbyResponseDTO> createLobby(Lobby lobby) {
        final CreateLobbyDTO createLobbyDTO = new CreateLobbyDTO(lobby.getName(), lobby.getPassword(), lobby.getSize());
        return createLobbyCommand.run(createLobbyDTO);
    }

    public Future<LobbyListDTO> fetchLobbyList() {
        return fetchLobbyCommand.run(new FetchLobbysDTO());
    }

}
