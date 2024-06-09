package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.CommandCallback;
import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientCommandImpl<V extends Resource, R extends Resource> implements ClientCommand<V, CommandCallback<R>> {
    private static final Logger logger = LoggerFactory.getLogger(ClientCommandImpl.class);
    protected final LobbyClient client;
    private final String commandName;
    public ClientCommandImpl(LobbyClient client, CommandNameEnum commandName) {
        this.client = client;
        this.commandName = commandName.name();
    }

    public CommandCallback<R> sendRequest(Resource argument) {
        logger.info("Sending command with {} and object {}", getName(), argument);
        return (CommandCallback<R>) client.sendRequest(argument, commandName);
    }



    @Override
    public CommandCallback<R> run(V argument) {
        return sendRequest(argument);
    }

    @Override
    public String getName() {
        return commandName;
    }
}