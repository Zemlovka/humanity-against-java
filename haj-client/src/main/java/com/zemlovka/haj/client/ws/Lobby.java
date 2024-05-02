package com.zemlovka.haj.client.ws;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private final String name;
    private String password = null;
    private final int size;
    private List<Player> players;

    public Lobby(String name, String password, int size) {
        this.name = name;
        this.password = password;
        this.size = size;
        this.players = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayerToList(Player player) {
        players.add(player);
    }

    public void removePlayerFromList(Player player) {
        players.remove(player);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getSize() {
        return size;
    }
}
