package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.utils.dto.Resource;
import javafx.application.Platform;

import java.util.concurrent.*;
import java.util.function.Function;


public class JavaFxAsyncFutureWrapper<T extends Resource> {
    private final CompletableFuture<T> parentFuture;
    public JavaFxAsyncFutureWrapper(CompletableFuture<T> parentFuture) {
        super();
        this.parentFuture = parentFuture;
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
    public <U> CompletableFuture<U> thenApply(Function<? super T, ? extends U> fn) {
        return parentFuture.thenApply(fn);
    }

    /**
     * If used in the javaFX thread, and you pass some javafxRendering in here then you must
     * wrap it with the Platform.runLater(), otherwise it will not run
     */
    public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return parentFuture.thenApplyAsync(fn);
    }
}
