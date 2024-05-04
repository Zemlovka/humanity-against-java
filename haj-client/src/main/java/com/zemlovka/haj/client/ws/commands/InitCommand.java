package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.client.InitDTO;
import com.zemlovka.haj.utils.dto.server.UserConnectionResponseDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public class InitCommand extends AbstractCommand<InitDTO, UserConnectionResponseDTO> {
    private static final String COMMAND_NAME = "Init";
    public InitCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public Future<UserConnectionResponseDTO> run(InitDTO argument) {
        return new CompletableFutureCastingWrapper<>(
                client.sendRequest(argument, UserConnectionResponseDTO.class, COMMAND_NAME)
        );
    }


}
