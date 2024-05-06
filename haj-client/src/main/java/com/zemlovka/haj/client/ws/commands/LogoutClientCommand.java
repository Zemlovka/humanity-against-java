package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.LogoutDTO;
import com.zemlovka.haj.utils.dto.server.LogoutResponseDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public class LogoutClientCommand extends AbstractClientCommand<LogoutDTO, LogoutResponseDTO> {
    private static final String COMMAND_NAME = CommandNameEnum.LOGOUT.name();
    public LogoutClientCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public CompletableFuture<LogoutResponseDTO> run(LogoutDTO argument) {
        return sendRequest(argument);
    }
}
