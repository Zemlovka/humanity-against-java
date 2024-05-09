package com.zemlovka.haj.server.game;

import java.util.concurrent.CompletableFuture;


public class Flags {
    private CompletableFuture<?> lobbyReadyFlag;
    private CompletableFuture<?> newPlayerFlag;

    public CompletableFuture<?> getLobbyReadyFlag() {
        if (lobbyReadyFlag == null)
            lobbyReadyFlag = new CompletableFuture<>();
        return lobbyReadyFlag;
    }

    public CompletableFuture<?> getNewPlayerFlag() {
        if (newPlayerFlag == null)
            newPlayerFlag = new CompletableFuture<>();
        return newPlayerFlag;
    }

    public void clearLobbyReadyFlag() {
        lobbyReadyFlag = null;
    }
    public void clearNewPlayerFlag() {
        newPlayerFlag = null;
    }
}
