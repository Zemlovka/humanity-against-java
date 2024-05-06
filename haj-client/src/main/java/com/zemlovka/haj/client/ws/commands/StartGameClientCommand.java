package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.StartGameDTO;
import com.zemlovka.haj.utils.dto.server.StartGameResponseDTO;

import java.util.concurrent.Future;


public class StartGameClientCommand extends AbstractClientCommand<StartGameDTO, StartGameResponseDTO> {
    private static final String COMMAND_NAME = CommandNameEnum.START_GAME.name();
    public StartGameClientCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public Future<StartGameResponseDTO> run(StartGameDTO argument) {
        return sendRequest(argument);
    }
}
