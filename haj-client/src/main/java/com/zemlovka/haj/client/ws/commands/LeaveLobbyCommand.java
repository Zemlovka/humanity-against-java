package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.LeaveLobbyDTO;
import com.zemlovka.haj.utils.dto.server.LeaveLobbyResponseDTO;


public class LeaveLobbyCommand extends AbstractClientCommand<LeaveLobbyDTO, LeaveLobbyResponseDTO> {
    private static final String COMMAND_NAME = CommandNameEnum.LEAVE_LOBBY.name();
    public LeaveLobbyCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public JavaFxAsyncFutureWrapper<LeaveLobbyResponseDTO> run(LeaveLobbyDTO argument) {
        return sendRequest(argument);
    }
}