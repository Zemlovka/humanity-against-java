package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import com.zemlovka.haj.client.ws.Player;
import javafx.scene.Scene;

import java.util.Stack;

public final class AppState {

    Player currentPlayer;
    Lobby currentLobby;
    private final Stack<Scene> sceneHistory = new Stack<>();
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
    public void pushScene(Scene scene) {
        sceneHistory.push(scene);
    }

    public Scene popScene() {
        if (!sceneHistory.isEmpty()) {
            return sceneHistory.pop();
        }
        return null; // Or throw an exception if you prefer
    }

    public boolean isSceneHistoryEmpty() {
        return sceneHistory.isEmpty();
    }
}
