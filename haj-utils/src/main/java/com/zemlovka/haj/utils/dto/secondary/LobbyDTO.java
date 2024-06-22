package com.zemlovka.haj.utils.dto.secondary;


import java.util.Set;


public class LobbyDTO {
    private String name;
    private int capacity;
    private Set<PlayerDTO> players;

    public LobbyDTO(String name, int capacity, Set<PlayerDTO> players) {
        this.name = name;
        this.capacity = capacity;
        this.players = players;
    }

    public LobbyDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerDTO> players) {
        this.players = players;
    }
}
