package com.zemlovka.haj.client.ws.entities;

/**
 * Represents a player in the game
 */
public class Player {
    private final String username;
    private final String avatar;
    private int score = 0;
    private boolean isClient = false;

    public Player(String username, String avatar, boolean isClient) {
        this.username = username;
        this.avatar = avatar;
        this.isClient = isClient;
    }
    public Player(String username, String avatar, boolean isClient, int score) {
        this.username = username;
        this.avatar = avatar;
        this.isClient = isClient;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }
    public String getAvatar() {
        return avatar;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public boolean isClient() {
        return isClient;
    }
}
