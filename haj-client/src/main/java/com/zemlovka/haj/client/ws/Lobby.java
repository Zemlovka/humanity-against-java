package com.zemlovka.haj.client.ws;

import javafx.collections.ObservableList;

public class Lobby {
    private String name;
    private String password = null;
    private int size;
    private ObservableList<Player> players;

    public Lobby(String name, String password, int size) {
        this.name = name;
        this.password = password;
        this.size = size;
    }

    public ObservableList<Player> getPlayers() {
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
