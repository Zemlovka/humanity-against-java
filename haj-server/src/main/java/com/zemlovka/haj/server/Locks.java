package com.zemlovka.haj.server;

import java.util.concurrent.CompletableFuture;


public class Locks {
    private CompletableFuture<?> lobbyCreationLock;

    public CompletableFuture<?> getLobbyCreationLock() {
        return lobbyCreationLock;
    }

    public void setLobbyCreationLock(CompletableFuture<?> lobbyCreationLock) {
        this.lobbyCreationLock = lobbyCreationLock;
    }
}
