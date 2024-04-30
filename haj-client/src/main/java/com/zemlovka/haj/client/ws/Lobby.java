package com.zemlovka.haj.client.ws;

import javafx.collections.ObservableList;

public class Lobby {
    private String name;
    private int maxPlayers;
    private ObservableList<Player> players;

    public Lobby(String name, int maxPlayers, ObservableList<Player> players) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.players = players;
    }
}
