package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.Resource;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public abstract class AbstractClientCommand<V extends Resource, R extends Resource> implements ClientCommand<V, Future<R>> {
    protected final LobbyClient client;
    private final String commandName;
    public AbstractClientCommand(LobbyClient client, String commandName) {
        this.client = client;
        this.commandName = commandName;
    }

    public CompletableFutureCastingWrapper<R> sendRequest(Resource argument) {
        return new CompletableFutureCastingWrapper<>(
                client.sendRequest(argument, commandName)
        );
    }



    @Override
    public abstract CompletableFuture<R> run(V argument);

    @Override
    public String getName() {
        return commandName;
    }
}