package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.CreateLobbyDTO;
import com.zemlovka.haj.utils.dto.server.CreateLobbyResponseDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


//todo not the acceptdto
public class CreateLobbyClientCommand extends AbstractClientCommand<CreateLobbyDTO, CreateLobbyResponseDTO> {
    private static final String COMMAND_NAME = CommandNameEnum.CREATE_LOBBY.name();
    public CreateLobbyClientCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public CompletableFuture<CreateLobbyResponseDTO> run(CreateLobbyDTO argument) {
        return sendRequest(argument);
    }
}
