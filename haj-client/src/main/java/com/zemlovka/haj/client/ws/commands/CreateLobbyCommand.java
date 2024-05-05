package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.AcceptDTO;
import com.zemlovka.haj.utils.dto.client.CreateLobbyDTO;
import com.zemlovka.haj.utils.dto.server.CreateLobbyResponseDTO;

import java.util.concurrent.Future;


//todo not the acceptdto
public class CreateLobbyCommand extends AbstractCommand<CreateLobbyDTO, CreateLobbyResponseDTO> {
    private static final String COMMAND_NAME = "CreateLobby";
    public CreateLobbyCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public Future<CreateLobbyResponseDTO> run(CreateLobbyDTO argument) {
        return new CompletableFutureCastingWrapper<>(
                client.sendRequest(argument, CreateLobbyDTO.class, COMMAND_NAME)
        );
    }
}
