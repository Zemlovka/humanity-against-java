package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.FetchLobbysDTO;
import com.zemlovka.haj.utils.dto.server.LobbyListDTO;

import java.util.concurrent.CompletableFuture;


public class FetchLobbyClientCommand extends AbstractClientCommand<FetchLobbysDTO, LobbyListDTO> {
    private static final String COMMAND_NAME = CommandNameEnum.FETCH_LOBBIES.name();
    public FetchLobbyClientCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public CompletableFuture<LobbyListDTO> run(FetchLobbysDTO argument) {
        return sendRequest(argument);
    }
}
