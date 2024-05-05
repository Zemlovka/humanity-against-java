package com.zemlovka.haj.server.game;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class Lobby {
    private int capacity;
    private String name;
    private String password;
    private ConcurrentHashMap<UUID, User> users;

    public Lobby(int capacity, String name, String password, ConcurrentHashMap<UUID, User> users) {
        this.capacity = capacity;
        this.name = name;
        this.password = password;
        this.users = users;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ConcurrentHashMap<UUID, User> getUsers() {
        return users;
    }
}
