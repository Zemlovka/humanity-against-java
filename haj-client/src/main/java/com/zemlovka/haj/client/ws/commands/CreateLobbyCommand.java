package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.AcceptDTO;
import com.zemlovka.haj.utils.dto.client.CreateLobbyDTO;

import java.util.concurrent.Future;


//todo not the acceptdto
public class CreateLobbyCommand extends AbstractCommand<CreateLobbyDTO, AcceptDTO> {
    private static final String COMMAND_NAME = "CreateLobby";
    public CreateLobbyCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public Future<AcceptDTO> run(CreateLobbyDTO argument) {
        return new CompletableFutureCastingWrapper<>(
                client.sendRequest(argument, AcceptDTO.class, COMMAND_NAME)
        );
    }
}
