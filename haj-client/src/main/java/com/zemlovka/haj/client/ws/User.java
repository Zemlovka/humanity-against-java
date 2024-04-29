package com.zemlovka.haj.client.ws;

public class User {
    private final String username;
    private final String avatar;
    private int score = 0;
    private boolean isClient = false;

    public User(String username, String avatar, boolean isClient) {
        this.username = username;
        this.avatar = avatar;
        this.isClient = isClient;
    }
}
