package com.zemlovka.haj.server.command.ws;

import com.zemlovka.haj.server.command.ResolvableCommand;
import com.zemlovka.haj.utils.Command;
import com.zemlovka.haj.utils.dto.Resource;

import java.util.concurrent.Future;


public abstract class AbstractWsCommand<V extends Resource, R extends Resource> implements ResolvableCommand<V, Future<R>> {
    protected final String client;
    private final String commandName;
    public AbstractWsCommand(String client, String commandName) {
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