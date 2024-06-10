package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Lobby;
import com.zemlovka.haj.client.ws.Player;

public final class AppState {
    Player currentPlayer;
    Lobby currentLobby;
    NotificationService notificationService;
    private AppState() {
        this.notificationService = new NotificationService();
    }
    private final static AppState INSTANCE = new AppState();
    public enum State {
        WAITING,
        CHOOSING,
        VOTING
    }
    private boolean canVote = false;
    private boolean canChoose = false;

    public boolean isCanVote() {
        return canVote;
    }

    public boolean isCanChoose() {
        return canChoose;
    }

    public void setCanChoose(boolean canChoose) {
        this.canChoose = canChoose;
    }

    public void setCanVote(boolean canVote) {
        this.canVote = canVote;
    }

    private State currentState = State.WAITING;

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getCurrentState() {
        return currentState;
    }

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

    public NotificationService getNotificationService() {
        return notificationService;
    }
}
