package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.Resource;


public abstract class AbstractClientCommand<V extends Resource, R extends Resource> implements ClientCommand<V, JavaFxAsyncFutureWrapper<R>> {
    protected final LobbyClient client;
    private final String commandName;
    public AbstractClientCommand(LobbyClient client, String commandName) {
        this.client = client;
        this.commandName = commandName;
    }

    public JavaFxAsyncFutureWrapper<R> sendRequest(Resource argument) {
        return (JavaFxAsyncFutureWrapper<R>) client.sendRequest(argument, commandName);
    }



    @Override
    public abstract JavaFxAsyncFutureWrapper<R> run(V argument);

    @Override
    public String getName() {
        return commandName;
    }
}