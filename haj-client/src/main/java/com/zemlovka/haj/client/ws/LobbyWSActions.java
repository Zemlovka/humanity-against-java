package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.ws.commands.CreateLobbyCommand;
import com.zemlovka.haj.client.ws.commands.FetchLobbyCommand;
import com.zemlovka.haj.client.ws.commands.InitCommand;
import com.zemlovka.haj.client.ws.commands.JoinLobbyCommand;
import com.zemlovka.haj.utils.dto.AcceptDTO;
import com.zemlovka.haj.utils.dto.client.InitDTO;
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
        final InitDTO initDTO = new InitDTO(username);
        return initCommand.run(initDTO);
    }

    public Future<AcceptDTO> createLobby() {
//        final
//        createLobbyCommand.run()
        return null;
    }
}
