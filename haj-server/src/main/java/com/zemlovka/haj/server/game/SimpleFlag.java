package com.zemlovka.haj.server.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;


/**
 * Flags for synchronization, when the signal executes it sets flag to null until the next onSignal is used
 */
public class SimpleFlag implements Flag {
    private static final Logger logger = LoggerFactory.getLogger(SimpleFlag.class);
    private CompletableFuture<?> flag;

    public synchronized void onSignal(Function<Object, ?> executeOnSignal) {
        if (flag == null)
            flag = new CompletableFuture<>();
        flag.thenApply(executeOnSignal);
    }


    public synchronized void signal() {
        try {
            if (flag != null)
                flag.complete(null);
            flag = null;
        } catch (Exception e) {
            logger.error("Error while executing flag executable", e);
        }
    }
}
