package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.JoinLobbyDTO;
import com.zemlovka.haj.utils.dto.server.JoinLobbyResponseDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public class JoinLobbyClientCommand extends AbstractClientCommand<JoinLobbyDTO, JoinLobbyResponseDTO> {
    private static final String COMMAND_NAME = CommandNameEnum.JOIN_LOBBY.name();
    public JoinLobbyClientCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public CompletableFuture<JoinLobbyResponseDTO> run(JoinLobbyDTO argument) {
        return sendRequest(argument);
    }
}
