package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.utils.dto.Resource;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class CompletableFutureCastingWrapper<T extends Resource> extends CompletableFuture<T> {
    private final Future<Resource> parentFuture;
    public CompletableFutureCastingWrapper(Future<Resource> parentFuture) {
        super();
        this.parentFuture = parentFuture;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return (T) parentFuture.get();
    }
}
