package com.zemlovka.haj.server.game;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class Lobby {
    private final int capacity;
    private final String name;
    private final String password;
    private final ConcurrentHashMap<UUID, User> users;
    private final Flags flags;

    public Lobby(int capacity, String name, String password, ConcurrentHashMap<UUID, User> users) {
        this.capacity = capacity;
        this.name = name;
        this.password = password;
        this.users = users;
        this.flags = new Flags();
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

    public Flags getFlags() {
        return flags;
    }
}
