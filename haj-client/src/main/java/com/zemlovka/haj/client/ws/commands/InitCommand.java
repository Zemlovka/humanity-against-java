package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.Client;
import com.zemlovka.haj.utils.dto.AcceptDTO;
import com.zemlovka.haj.utils.dto.client.InitDTO;

import java.util.concurrent.Future;


public class InitCommand extends AbstractCommand<InitDTO, AcceptDTO> {
    private static final String COMMAND_NAME = "Init";
    public InitCommand(Client client) {
        super(client, COMMAND_NAME);
    }

    @Override
    public Future<AcceptDTO> run(InitDTO argument) {
        return null;
    }
}
