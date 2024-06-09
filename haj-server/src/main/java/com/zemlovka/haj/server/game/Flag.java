package com.zemlovka.haj.server.game;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;


public interface Flag {
    void onSignal(Function<Object, ?> executeOnSignal);

    void signal();
}
