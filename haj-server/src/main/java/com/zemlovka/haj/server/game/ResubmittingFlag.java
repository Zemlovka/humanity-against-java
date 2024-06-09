package com.zemlovka.haj.server.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;


public class ResubmittingFlag extends Flag{
    private static final Logger logger = LoggerFactory.getLogger(ResubmittingFlag.class);
    private CompletableFuture<?> flag;
    private final Set<Function<Object, ?>> thenApplySet;

    public ResubmittingFlag() {
        thenApplySet = new HashSet<>();
    }

    @Override
    public synchronized void onSignal(Function<Object, ?> executeOnSignal) {
        if (flag == null)
            flag = new CompletableFuture<>();
        thenApplySet.add(executeOnSignal);
        flag.thenApply(executeOnSignal);
    }

    @Override
    public synchronized void signal() {
        try {
            if (flag != null) {
                flag.complete(null);
                flag = new CompletableFuture<>();
                thenApplySet.forEach(f -> flag.thenApply(f));
            }
        } catch (Exception e) {
            logger.error("Error while executing flag executable", e);
        }
    }
}
