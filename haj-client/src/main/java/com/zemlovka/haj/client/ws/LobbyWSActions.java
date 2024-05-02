package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.ws.commands.InitCommand;
import com.zemlovka.haj.utils.dto.client.InitDTO;
import com.zemlovka.haj.utils.dto.server.UserConnectionResponseDTO;

import java.util.concurrent.Future;


public class LobbyWSActions {

    private LobbyClient client;


    private InitCommand initCommand;
    public LobbyWSActions() {
        LobbyClient client = new LobbyClient();
        client.start(); //will it work?
        this.client = client;
        initCommand = new InitCommand(client);
    }

    public Future<UserConnectionResponseDTO> login(String username) {
        final InitDTO initDTO = new InitDTO(username);
        return initCommand.run(initDTO);
    }
}
