package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.LoginDTO;
import com.zemlovka.haj.utils.dto.server.LoginResponseDTO;

import java.util.concurrent.Future;


public class LoginCommand extends AbstractCommand<LoginDTO, LoginResponseDTO> {
    private static final String COMMAND_NAME = CommandNameEnum.LOGIN.name();
    public LoginCommand(LobbyClient client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public Future<LoginResponseDTO> run(LoginDTO argument) {
        return sendRequest(argument);
    }


}
