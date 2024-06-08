package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.JavaFxAsyncFutureWrapper;
import com.zemlovka.haj.client.ws.LobbyClient;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.Resource;


public class ClientCommandImpl<V extends Resource, R extends Resource> implements ClientCommand<V, JavaFxAsyncFutureWrapper<R>> {
    protected final LobbyClient client;
    private final String commandName;
    public ClientCommandImpl(LobbyClient client, CommandNameEnum commandName) {
        this.client = client;
        this.commandName = commandName.name();
    }

    public JavaFxAsyncFutureWrapper<R> sendRequest(Resource argument) {
        return (JavaFxAsyncFutureWrapper<R>) client.sendRequest(argument, commandName);
    }



    @Override
    public JavaFxAsyncFutureWrapper<R> run(V argument) {
        return sendRequest(argument);
    }

    @Override
    public String getName() {
        return commandName;
    }
}