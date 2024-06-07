package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.utils.dto.Resource;

import java.util.concurrent.*;
import java.util.function.Function;


public class CompletableFutureCastingWrapper<T extends Resource> extends CompletableFuture<T> {
    private final CompletableFuture<T> parentFuture;
    public CompletableFutureCastingWrapper(CompletableFuture<T> parentFuture) {
        super();
        this.parentFuture = parentFuture;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return (T) parentFuture.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return (T) parentFuture.get(timeout, unit);
    }

    @Override
    public <U> CompletableFuture<U> thenApply(Function<? super T, ? extends U> fn) {
        return parentFuture.thenApply(fn);
    }

    @Override
    public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return parentFuture.thenApplyAsync(fn);
    }
}
