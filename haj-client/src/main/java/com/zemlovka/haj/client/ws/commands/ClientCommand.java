package com.zemlovka.haj.client.ws.commands;

public interface ClientCommand<V, R> {
    R run(V argument);

    String getName();
}
