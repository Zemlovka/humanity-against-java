package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.utils.CommunicationObject;
import com.zemlovka.haj.utils.dto.Resource;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Function;


public class CommandCallback<T extends Resource> {
    private CompletableFuture<T> parentFuture;
    private final Set<Function<? super T, ?>> thenApplySet;
    private final String commandName;
    private final UUID uuid;
    private boolean killResubmit;

    public CommandCallback(String commandName, UUID uuid) {
        this.parentFuture = new CompletableFuture<>();
        this.commandName = commandName;
        this.uuid = uuid;
        thenApplySet = new HashSet<>();
    }

    public T get() throws InterruptedException, ExecutionException {
        return parentFuture.get();
    }

    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return parentFuture.get(timeout, unit);
    }

    /**
     * If used in the javaFX thread, and you pass some javafxRendering in here then you must
     * wrap it with the Platform.runLater(), otherwise it will not run
     */
    public CompletableFuture<?> thenApply(Function<? super T, ?> fn) {
        thenApplySet.add(fn);
        return parentFuture.thenApply(fn);
    }

    /**
     * If used in the javaFX thread, and you pass some javafxRendering in here then you must
     * wrap it with the Platform.runLater(), otherwise it will not run
     */
    public CompletableFuture<?> thenApplyAsync(Function<? super T, ?> fn) {
        thenApplySet.add(fn);
        return parentFuture.thenApplyAsync(fn);
    }

    public synchronized void complete(CommunicationObject<T> communicationObject, ConcurrentHashMap<UUID, CommandCallback<Resource>> callbacks) {
        parentFuture.complete(communicationObject.body());
        if (communicationObject.body().isPolling()) {
            parentFuture = new CompletableFuture<>();
            thenApplySet.forEach(f -> parentFuture.thenApply(f));
        }
    }

    public String getCommandName() {
        return commandName;
    }

    @Override
    public String toString() {
        return "Callback{" +
                "commandName='" + commandName + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
