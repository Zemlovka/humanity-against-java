package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import com.zemlovka.haj.client.ws.Player;
import javafx.scene.Scene;

import java.util.Stack;

public final class AppState {

    Player currentPlayer;
    Lobby currentLobby;
    private final static AppState INSTANCE = new AppState();

    private AppState() {}

    public static AppState getInstance() {
        return INSTANCE;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }

}
