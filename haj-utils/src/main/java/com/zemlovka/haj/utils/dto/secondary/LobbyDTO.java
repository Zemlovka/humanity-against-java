package com.zemlovka.haj.utils.dto.secondary;


import java.util.Set;


public class LobbyDTO {
    private String name;
    private int capacity;
    private Set<PlayerDTO> players;
    private boolean isLocked;

    public LobbyDTO(String name, int capacity, Set<PlayerDTO> players, boolean isLocked) {
        this.name = name;
        this.capacity = capacity;
        this.players = players;
        this.isLocked = isLocked;
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

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
