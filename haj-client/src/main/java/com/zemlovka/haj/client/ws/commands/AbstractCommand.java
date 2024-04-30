package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.client.ws.Client;
import com.zemlovka.haj.utils.Command;
import com.zemlovka.haj.utils.dto.Resource;

import java.util.concurrent.Future;


public abstract class AbstractCommand<V extends Resource, R extends Resource> implements Command<V, Future<R>> {
    protected final Client client;
    private final String commandName;
    public AbstractCommand(Client client, String commandName) {
        this.client = client;
        this.commandName = commandName;
    }
    @Override
    public abstract Future<R> run(V argument);

    @Override
    public String getName() {
        return commandName;
    }
}