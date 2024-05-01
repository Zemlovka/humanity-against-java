package com.zemlovka.haj.client.ws;

public class LobbyWSActions {

    private LobbyClient client;
    public LobbyWSActions() {
        LobbyClient client = new LobbyClient();
        client.start(); //will it work?
        this.client = client;
    }

    public void connect(String username) {
        client.setMessage("USER " + username);
    }
}
