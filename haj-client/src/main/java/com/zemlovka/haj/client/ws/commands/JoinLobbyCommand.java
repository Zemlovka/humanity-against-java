package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.Client;
import com.zemlovka.haj.utils.dto.AcceptDTO;
import com.zemlovka.haj.utils.dto.client.JoinLobbyDTO;

import java.util.concurrent.Future;


//todo returns not the acceptdto
public class JoinLobbyCommand extends AbstractCommand<JoinLobbyDTO, AcceptDTO> {
    private static final String COMMAND_NAME = "JoinLobby";
    public JoinLobbyCommand(Client client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public Future<AcceptDTO> run(JoinLobbyDTO argument) {
        return null;
    }
}
