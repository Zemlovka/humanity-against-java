package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.Command;
import com.zemlovka.haj.utils.dto.Resource;
import com.zemlovka.haj.utils.dto.client.LoginDTO;

import java.util.concurrent.Future;


public abstract class AbstractCommand<V extends Resource, R extends Resource> implements Command<V, Future<R>> {
    protected final LobbyClient client;
    private final String commandName;
    public AbstractCommand(LobbyClient client, String commandName) {
        this.client = client;
        this.commandName = commandName;
    }

    public Future<R> sendRequest(Resource argument) {
        return new CompletableFutureCastingWrapper<>(
                client.sendRequest(argument, commandName)
        );
    }



    @Override
    public abstract Future<R> run(V argument);

    @Override
    public String getName() {
        return commandName;
    }
}