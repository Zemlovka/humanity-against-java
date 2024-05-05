package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.StartGameDTO;
import com.zemlovka.haj.utils.dto.server.StartGameResponseDTO;

import java.util.concurrent.Future;


public class StartGameCommand extends AbstractCommand<StartGameDTO, StartGameResponseDTO> {
    private static final String COMMAND_NAME = CommandNameEnum.START_GAME.name();
    public StartGameCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public Future<StartGameResponseDTO> run(StartGameDTO argument) {
        return sendRequest(argument);
    }
}
