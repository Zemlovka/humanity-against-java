package com.zemlovka.haj.server.game;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;


/**
 * Flags for synchronization, when the signal executes it sets flag to null until the next onSignal is used
 */
public class Flag {
    private CompletableFuture<?> flag;
    public <U> void onSignal(Function<Object, ? extends U> executeOnSignal) {
        if (flag == null)
            flag = new CompletableFuture<>();
        flag.thenApply(executeOnSignal);
        flag = null;
    }

    public void signal() {
        if (flag != null)
            flag.complete(null);
    }
}
