package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.fx.notificationService.NotificationService;
import com.zemlovka.haj.client.ws.entities.Lobby;
import com.zemlovka.haj.client.ws.entities.Player;

/**
 * Singleton class that holds the current state of the application. Inspired by Redux pattern in React.
 * Is used to pass data between controllers and client components.
 * <p>
 * @author Nikita Korotov
 * @version 1.0
 */
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
