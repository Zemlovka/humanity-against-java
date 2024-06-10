package com.zemlovka.haj.server.game;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;


public class User {
    private String username;
    private UUID uuid;
    private Lobby currentLobby;

    private ScheduledExecutorService lobbyPollingExecutor;

    public boolean isLoggedIn() {
        return username != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }

    public void setLobbyPollingExecutor(ScheduledExecutorService lobbyPollingExecutor) {
        this.lobbyPollingExecutor = lobbyPollingExecutor;
    }

    public void shutdownLobbyPollingExecutor() {
        if (lobbyPollingExecutor != null)
            lobbyPollingExecutor.shutdown();
    }
}
