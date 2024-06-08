package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.FetchPlayersDTO;
import com.zemlovka.haj.utils.dto.server.FetchPlayersResponseDTO;


public class FetchPlayersCommand extends AbstractClientCommand<FetchPlayersDTO, FetchPlayersResponseDTO> {
    private static final String COMMAND_NAME = CommandNameEnum.FETCH_PLAYERS.name();
    public FetchPlayersCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public JavaFxAsyncFutureWrapper<FetchPlayersResponseDTO> run(FetchPlayersDTO argument) {
        return sendRequest(argument);
    }
}
