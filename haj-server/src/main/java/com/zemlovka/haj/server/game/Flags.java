package com.zemlovka.haj.server.game;

import java.util.concurrent.CompletableFuture;


public class Flags {
    private CompletableFuture<?> lobbyReadyFlag;

    public CompletableFuture<?> getLobbyReadyFlag() {
        if (lobbyReadyFlag == null)
            lobbyReadyFlag = new CompletableFuture<>();
        return lobbyReadyFlag;
    }

    public void clearLobbyReadyFlag() {
        lobbyReadyFlag = null;
    }
}
